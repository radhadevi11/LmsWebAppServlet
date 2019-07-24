package com.glosys.lmsweb;

import com.glosys.lms.CourseCategory;
import com.glosys.lms.controller.CourseCategoryController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/addCoursePage")

public class AddCoursePageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        CourseCategoryController courseCategoryController = new CourseCategoryController();
        List<CourseCategory> courseCategories = courseCategoryController.getCourseCategories();

        String courseName = request.getParameter("courseName");
        String courseCode = request.getParameter("courseCode");
        String syllabus = request.getParameter("syllabus");
        Integer courseCategoryId = 0 ;
        boolean workshopEligibility = Boolean.parseBoolean(request.getParameter("workshop_eligibility"));
        boolean inplantTrainingEligibility = Boolean.parseBoolean(request.getParameter("inplant_training_eligibility"));
        boolean corporateTrainingEligibility = Boolean.parseBoolean(request.getParameter("corporate_training_eligibility"));
        boolean researchTrainingEligibility = Boolean.parseBoolean(request.getParameter("research_training_eligibility"));

        if((request.getParameter("cc") != null)) {
           courseCategoryId = Integer.parseInt(request.getParameter("cc"));
        }

        if(courseName == null){
            courseName = "";
        }
        if(courseCode == null){
            courseCode = "";
        }
        if(syllabus == null){
            syllabus = "";
        }
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<style>\n" +
                "input[type=text], select {\n" +
                "  width: 100%;\n" +
                "  padding: 12px 20px;\n" +
                "  margin: 8px 0;\n" +
                "  display: inline-block;\n" +
                "  border: 1px solid #ccc;\n" +
                "  border-radius: 4px;\n" +
                "  box-sizing: border-box;\n" +
                "}\n" +
                "\n" +
                "input[type=submit] {\n" +
                "  width: 100%;\n" +
                "  background-color: #4CAF50;\n" +
                "  color: white;\n" +
                "  padding: 14px 20px;\n" +
                "  margin: 8px 0;\n" +
                "  border: none;\n" +
                "  border-radius: 4px;\n" +
                "  cursor: pointer;\n" +
                "}\n" +
                "\n" +
                "input[type=submit]:hover {\n" +
                "  background-color: #45a049;\n" +
                "}\n" +
                "\n" +
                "div {\n" +
                "  border-radius: 5px;\n" +
                "  background-color: #f2f2f2;\n" +
                "  padding: 20px;\n" +
                "}\n" +
                "</style>\n" +
                "<body>\n" +
                "\n" +
                "<h3><center>Add Course</center></h3>\n" +
                "\n" +
                "<div>\n" +
                "    <form action=\"addCourse\">\n" +
                "        <form action=\"addcourse\" method=\"POST\">\n" +
                "\n"+
                "<input type=\"text\" name=\"courseName\" value=\""+courseName+"\" placeholder=\"Course name\"><br>\n" +
                "            <input type=\"text\" name=\"courseCode\" value=\""+courseCode+"\" placeholder=\"Course code\"  ><br>\n" +
                "            <span style=\"font-size:18px;\">Syllabus</span>\n" +
                "            <br>\n" +
                "            <textarea name=\"syllabus\" rows=\"10\" cols=\"30\" placeholder=\"Syllabus\" >\n" +
                syllabus+"\n"+
                "</textarea>\n" +
                "            <br>\n" +
                "            <span style=\"font-size:18px;\">Training Course</span>\n" +
                "            <br>\n" );

        out.println( "            <select name=\"cc\">\n");
        out.println(     "\n" );
        for (CourseCategory courseCategory : courseCategories){
            if(courseCategoryId == courseCategory.getId() ) {
                out.println("<option value=\"" + courseCategory.getId() + "\" selected>" + courseCategory.getName() + "</option> ");
            }
            else {
                out.println("<option value=\"" + courseCategory.getId() + "\">" + courseCategory.getName() + "</option>");
            }
        }
        out.println("</select>\n" );
        if (workshopEligibility == true) {
            out.println("<input type=\"checkbox\" name=\"workshop_eligibility\" value=\"TRUE\" checked >Workshop Eligibility<br>\n");
        }
        else {
            out.println("<input type=\"checkbox\" name=\"workshop_eligibility\" value=\"TRUE\" >Workshop Eligibility<br>\n");
        }
        if(inplantTrainingEligibility == true) {
            out.println(" <input type=\"checkbox\" name=\"inplant_training_eligibility\" value=\"TRUE\" checked >Inplant Training Eligibility<br>\n");
        }
        else {
            out.println(" <input type=\"checkbox\" name=\"inplant_training_eligibility\" value=\"TRUE\" >Inplant Training Eligibility<br>\n");
        }
        if(corporateTrainingEligibility == true) {
            out.println("<input type=\"checkbox\" name=\"corporate_training_eligibility\" value=\"TRUE\" checked>Corporate Training Eligibility<br>\n");
        }
        else {
            out.println("<input type=\"checkbox\" name=\"corporate_training_eligibility\" value=\"TRUE\">Corporate Training Eligibility<br>\n");
        }
        if(researchTrainingEligibility == true) {
            out.println("<input type=\"checkbox\" name=\"research_training_eligibility\" value=\"TRUE\" checked>Research Training Eligibility<br>\n");
        }
        else {
            out.println("<input type=\"checkbox\" name=\"research_training_eligibility\" value=\"TRUE\">Research Training Eligibility<br>\n");
        }
        out.println(" <br>\n" +
                "            <br>\n" +
                "            <input type=\"submit\" value=\"Save\" >\n" +
                "\n" +
                "        </form>\n" +
                "    </form>\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n");

    }

}
