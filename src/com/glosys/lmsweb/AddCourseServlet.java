package com.glosys.lmsweb;

import com.glosys.lms.Course;
import com.glosys.lms.CourseCategory;
import com.glosys.lms.controller.CourseCategoryController;
import com.glosys.lms.controller.CourseController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/addCourse")

public class AddCourseServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CourseCategoryController courseCategoryController = new CourseCategoryController();
        List<CourseCategory> courseCategories = courseCategoryController.getCourseCategories();
        Course course = new Course("","","",new CourseCategory(5),false,false,false,false);
        PrintWriter out = response.getWriter();
        out.println(showPage(course,courseCategories));
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CourseController courseController = new CourseController();

        String courseName = request.getParameter("courseName");
        String courseCode = request.getParameter("courseCode");
        String syllabus = request.getParameter("syllabus");
        Integer courseCategoryId = Integer.parseInt(request.getParameter("cc"));
        boolean workshopEligibility = Boolean.parseBoolean(request.getParameter("workshop_eligibility"));
        boolean inplantTrainingEligibility = Boolean.parseBoolean(request.getParameter("inplant_training_eligibility"));
        boolean corporateTrainingEligibility = Boolean.parseBoolean(request.getParameter("corporate_training_eligibility"));
        boolean researchTrainingEligibility = Boolean.parseBoolean(request.getParameter("research_training_eligibility"));

        Course course = new Course(courseName,
                courseCode,
                syllabus,
                new CourseCategory(courseCategoryId),
                workshopEligibility,
                researchTrainingEligibility,
                inplantTrainingEligibility,
                corporateTrainingEligibility);

        CourseCategoryController courseCategoryController = new CourseCategoryController();
        List<CourseCategory> courseCategories = courseCategoryController.getCourseCategories();


        if(courseController.isExistingCourse(courseCode)){
            PrintWriter out = response.getWriter();
            request.setAttribute("isExistingCourse",true);
            out.println(showPage(course,courseCategories));

        }
        else {
            courseController.saveCourse(course);
        }

    }

    public String showPage(Course course, List<CourseCategory> courseCategories){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "  <link rel=\"stylesheet\" type=\"text/css\" href=\"addCourseForm.css\">\n" +
                "</head>"+
                "<body>\n" +
                "\n" +
                "<h3><center>Add Course</center></h3>\n" +
                "\n" +
                "<div>\n" +

                "        <form action=\"addCourse\" method=\"POST\">\n" +
                "\n"+
                "<input type=\"text\" name=\"courseName\" value=\""+course.getName()+"\" placeholder=\"Course name\"><br>\n" +
                "            <input type=\"text\" name=\"courseCode\" value=\""+course.getCode()+"\" placeholder=\"Course code\"  ><br>\n" +
                "            <span style=\"font-size:18px;\">Syllabus</span>\n" +
                "            <br>\n" +
                "            <textarea name=\"syllabus\" rows=\"10\" cols=\"30\" placeholder=\"Syllabus\" >\n" +
                course.getSyllabus()+"\n"+
                "</textarea>\n" +
                "            <br>\n" +
                "            <span style=\"font-size:18px;\">Training Course</span>\n" +
                "            <br>\n" +courseCategoryDropDown(course, courseCategories)+

         workShopCheckBox(course)+inplantTrainingCheckBox(course) + corporateTrainingCheckBox(course)+
                researchTrainingCheckBox(course)+

        " <br>\n" +
                "            <br>\n" +
                "            <input type=\"submit\" value=\"Save\" >\n" +
                "\n" +
                "        </form>\n" +
                "    </form>\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";

    }

    private String corporateTrainingCheckBox(Course course) {
        if(course.isCorporateTrainingEligibility() == true) {
           return "<input type=\"checkbox\" name=\"corporate_training_eligibility\" value=\"TRUE\" checked>Corporate Training Eligibility<br>\n";
        }
        else {
           return  "<input type=\"checkbox\" name=\"corporate_training_eligibility\" value=\"TRUE\">Corporate Training Eligibility<br>\n";
        }
    }

    private String workShopCheckBox(Course course) {
        if (course.isWorkshopEligibility() == true) {
            return "<input type=\"checkbox\" name=\"workshop_eligibility\" value=\"TRUE\" checked >Workshop Eligibility<br>\n";
        }
        else {
            return "<input type=\"checkbox\" name=\"workshop_eligibility\" value=\"TRUE\" >Workshop Eligibility<br>\n";
        }
    }

    private String inplantTrainingCheckBox(Course course){
        if(course.isInplantTrainingEligibility() == true) {
           return  " <input type=\"checkbox\" name=\"inplant_training_eligibility\" value=\"TRUE\" checked >Inplant Training Eligibility<br>\n";
        }
        else {
            return " <input type=\"checkbox\" name=\"inplant_training_eligibility\" value=\"TRUE\" >Inplant Training Eligibility<br>\n";
        }
    }

    private String researchTrainingCheckBox(Course course){
        if(course.isResearchTrainingEligibility() == true) {
            return "<input type=\"checkbox\" name=\"research_training_eligibility\" value=\"TRUE\" checked>Research Training Eligibility<br>\n";
        }
        else {
           return  "<input type=\"checkbox\" name=\"research_training_eligibility\" value=\"TRUE\">Research Training Eligibility<br>\n";
        }
    }

    private String courseCategoryDropDown(Course course, List<CourseCategory> courseCategories) {
        String dropDown =  "<select name=\"cc\">\n";
        for (CourseCategory courseCategory : courseCategories){
            if(course.getCourseCategory().getId() == courseCategory.getId() ) {
               dropDown += "<option value=\"" + courseCategory.getId() + "\" selected>" + courseCategory.getName() + "</option> ";
            }
            else {
                dropDown += "<option value=\"" + courseCategory.getId() + "\">" + courseCategory.getName() + "</option>";
            }
        }
        return dropDown+ "</select>\n";
    }
}
