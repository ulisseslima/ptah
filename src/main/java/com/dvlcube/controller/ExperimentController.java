package com.dvlcube.controller;

import com.dvlcube.security.PasswordService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wonka
 */
public class ExperimentController extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text;charset=UTF-8");
        String assunto = request.getParameter("assunto");
        String subject = request.getParameter("subject");
        String gI = request.getParameter("gi");
        String gD = request.getParameter("gd");

        if (assunto != null) {
            response.getWriter().write(getAnswer(assunto, "pt"));
            System.out.println("## Como funciona: ".concat(assunto));
        }
        if (subject != null) {
            subject = subject.replaceFirst("/", "");
            response.getWriter().write(getAnswer(subject, "en"));
            System.out.println("## How does it work: ".concat(subject));
        }

        if (gI != null) {//inicia-se o programa das galinhas
            int gi, gd, meses = 0, ovos = 0, aux;
            try {
                assert meses > 0;
            } catch (AssertionError e) {
                e.fillInStackTrace();
            }
            gi = Integer.parseInt(gI);
            aux = gi;
            gd = Integer.parseInt(gD);
            StringBuilder builder = new StringBuilder();
            while (gi < gd) {
                builder.append("<h1>## Meses transcorridos: ").append(meses);
                builder.append("<br>Total de ovos: ").append(ovos);
                builder.append(";<br>Total de galinhas: ").append(gi).append("</h1>");
                if (meses > 0) {
                    gi = gi - ((gi * 10) / 100);
                    builder.append("<ul><li>10% das galinhas morreram. Restam ").append(gi).append(" galinhas;</li>");
                    ovos += gi;
                    builder.append("<li>").append(gi).append(" galinhas colocaram ").append(gi).append(" ovos;</li><li>");
                    builder.append(ovos).append(" ovos no total;</li></ul>");
                    if (meses % 2 == 0) {
                        int aux2 = gi;
                        builder.append("<h2>## Passaram-se 2 meses...</h2>");
                        gi += aux;
                        builder.append("<ul><li>Nasceram ").append(aux).append(" galinhas de ").append(ovos).append(" ovos;</li>");
                        ovos -= aux;
                        builder.append("<li>Quantidade de ovos reduzida para ").append(ovos);
                        aux = aux2;
                        builder.append("</li><li>Em dois meses, ").append(aux).append(" virarao galinhas;</li></ul>");
                    }
                }
                builder.append("<hr>");
                meses++;
            }
            builder.append("<h1>Resultado final:</h1><br>Meses: ").append(meses);
            builder.append(";<br>Galinhas: ").append(gi);
            response.getWriter().write(builder.toString());
        }

        String string = request.getParameter("string");
        if (string != null) {
            if (string.startsWith(";")) {
                string = string.replaceFirst(";", "");
            }
            if (string.equals("audio")) {
                response.getWriter().write("<audio src=\"www.dvlcube.com/ptah/usr/etc/FranceGall-j_entends_cette_musique.ogv\" controls=\"controls\">Your browser does not support the audio element.</audio>");
            } else {
                try {
                    response.getWriter().write("String: ".concat(PasswordService.getInstance().encrypt(string)));
                } catch (Exception e) {
                }
            }
        }


    }

    private String createQuestion(String subject, String lang) {
        String question;
        if (lang.equals("pt")) {
            question = "<h1>Como " + subject + " funciona?</h1>";
        } else {
            question = "<h1>How does " + subject + " work?</h1>";
        }
        return question;
    }

    private String getAnswer(String subject, String lang) {
        String answer = "<html><head><meta charset=\"UTF-8\"/>"
                + "<meta name='description' lang='en' content='Get answers to any question. No matter how complex they are.'>"
                + "<meta name='description' lang='pt' content='Consiga respostas para qualquer pergunta. Independente de quão complexas elas possam ser.'>"
                + "<meta name='keywords' content='respostas, answers, technical question, pergunta técnica, cartoon, asbtruse goose'>"
                + "<title>" + subject + "</title>"
                + "<style>body{font-family:cursive;}div{width:470px;border:3px solid;}h1{word-wrap: break-word;text-transform:uppercase;font-size:x-large;font-weight:normal;padding:0 1em 0 2em;margin:0;text-align:right;}</style>"
                + "</head><body><div>"
                + createQuestion(subject, lang)
                + "<img src=\"usr/etc/under_the_hood-" + lang + ".gif\" alt=\"magic\"/></div>Inspired by a cartoon from Abstruse Goose: <a href=\"http://abstrusegoose.com/secret-archives/under-the-hood\">abstruse goose</a></body></html>";
        return answer;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
