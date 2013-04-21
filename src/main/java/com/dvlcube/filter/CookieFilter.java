package com.dvlcube.filter;

import java.io.IOException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @since Aug 21, 2011
 * @author Wonka
 */
public class CookieFilter implements Filter {

    public static final String PAGE_SPEED_COOKIE = "PageSpeedCookie";
    public static final String PAGE_SPEED_SESSION = "PageSpeedSession";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            Cookie[] cookies = ((HttpServletRequest) request).getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(PAGE_SPEED_COOKIE)) {
                    calculatePageSpeed(new SpeedCookie(cookie), ((HttpServletRequest) request).getSession());
                    break;
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    /**
     * Does stuff with the cookie.
     * @param cookie
     * @param session 
     */
    private void calculatePageSpeed(SpeedCookie cookie, HttpSession session) {
        Map<String, PageSpeedInfo> pagesSpeed = (Map<String, PageSpeedInfo>) session.getAttribute(PAGE_SPEED_SESSION);
        if (pagesSpeed == null) {
            pagesSpeed = new HashMap<String, PageSpeedInfo>();
            PageSpeedInfo page = pagesSpeed.get(cookie.getPage());
            if (page != null) {
                page.update(cookie);
            } else {
                page = new PageSpeedInfo(cookie);
            }
            pagesSpeed.put(cookie.getPage(), page);
            session.setAttribute(PAGE_SPEED_SESSION, pagesSpeed);
            System.out.println(page);
        }
    }

    private class PageSpeedInfo {

        private Speed speed = new Speed();
        private Info info = new Info();

        public PageSpeedInfo(SpeedCookie cookie) {
            speed.minimum = cookie.millis;
            speed.average = cookie.millis;
            speed.maximum = cookie.millis;

            info.name = cookie.page;
        }

        private void update(SpeedCookie cookie) {
            if (cookie.getMillis() < speed.minimum) {
                speed.minimum = cookie.getMillis();
            } else if (cookie.getMillis() > speed.maximum) {
                speed.maximum = cookie.getMillis();
            }
            speed.average = (speed.minimum + speed.maximum) / 2;
            speed.last = cookie.getMillis();
        }

        private class Speed {

            long minimum;
            long average;
            long maximum;
            long last;

            public String getMininum() {
                return toString(minimum);
            }

            public String getAverage() {
                return toString(average);
            }

            public String getMaximum() {
                return toString(maximum);
            }

            public String getLast() {
                return toString(last);
            }

            private String toString(long millis) {
                return millis + "ms.";
            }

            @Override
            public String toString() {
                return new Formatter().format(
                        "Minimum: %d\nAverage: %d\nMaximum: %d\nLast: %d\n", 
                        minimum, 
                        average, 
                        maximum, 
                        last).toString();
            }
        }

        private class Info {

            String name;
            String ip;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            PageSpeedInfo page = (PageSpeedInfo) o;
            if (o instanceof PageSpeedInfo) {
                if (page.info != null) {
                    return page.info.name.equals(this.info.name);
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 67 * hash + (this.info != null ? this.info.hashCode() : 0);
            return hash;
        }
    }

    /**
     * Representa um cookie de velocidade de carregamento da página.
     */
    private class SpeedCookie {

        /**
         * @param cookie a cookie with the correct information.
         */
        SpeedCookie(Cookie cookie) {
            page = cookie.getValue().split("@")[0];
            millis = Long.parseLong(cookie.getValue().split("@")[1]);
        }
        private String page;
        private long millis;

        public String getPage() {
            return page;
        }

        public long getMillis() {
            return millis;
        }
    }
}
