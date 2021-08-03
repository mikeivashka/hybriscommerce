<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container" style="font-size: ${questionsFontSize}">
    <c:forEach var="question" varStatus="i" begin="0" end="${questionsToShow}" items="${product.questions}">
        <i>Question:</i><br/>
        <b>${question.questionAuthor.firstName} ${question.questionAuthor.lastName}:</b>
        ${question.question}<br/>
        <c:if test="${not empty question.answer}">
            <i>Answer:</i><br/>
            <b>${question.answerAuthor.firstName} ${question.answerAuthor.lastName}:</b>
            ${question.answer}<br/>
        </c:if>
        <br/>
    </c:forEach>
</div>