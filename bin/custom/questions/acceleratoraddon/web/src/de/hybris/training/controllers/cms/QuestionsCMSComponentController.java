package de.hybris.training.controllers.cms;

import de.hybris.platform.addonsupport.controllers.cms.AbstractCMSAddOnComponentController;
import de.hybris.training.controllers.QuestionsControllerConstants;
import de.hybris.training.model.QuestionsCMSComponentModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller(QuestionsCMSComponentModel._TYPECODE +"Controller")
@RequestMapping(value= QuestionsControllerConstants.Actions.Cms.QuestionsComponent)
public class QuestionsCMSComponentController extends AbstractCMSAddOnComponentController<QuestionsCMSComponentModel> {
    @Override
    protected void fillModel(HttpServletRequest request, Model model, QuestionsCMSComponentModel component) {
        model.addAttribute("questionsToShow", component.getNumberOfQuestionsToShow());
        model.addAttribute("questionsFontSize", component.getFontSize());
    }
}
