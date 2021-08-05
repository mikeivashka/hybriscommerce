package de.hybris.training.interceptor;

import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;
import de.hybris.platform.servicelayer.user.UserService;
import org.springframework.beans.factory.annotation.Required;

import java.util.HashSet;
import java.util.Set;

import static de.hybris.training.constants.QuestionsConstants.HYBRIDS_USER_GROUP;

public class HybridsCustomerPrepareInterceptor implements PrepareInterceptor<CustomerModel> {

    private UserService userService;

    @Override
    public void onPrepare(CustomerModel customerModel, InterceptorContext interceptorContext) {
        final UserGroupModel hybridsGroupModel = userService.getUserGroupForUID(HYBRIDS_USER_GROUP);
        if (customerModel.getIsInternal()) {
            if (!customerModel.getGroups().contains(hybridsGroupModel)) {
                final Set<PrincipalGroupModel> newGroups = new HashSet<>(customerModel.getGroups());
                newGroups.add(hybridsGroupModel);
                customerModel.setGroups(newGroups);
            }
        } else if (customerModel.getGroups().contains(hybridsGroupModel)) {
            final Set<PrincipalGroupModel> newGroups = new HashSet<>(customerModel.getGroups());
            newGroups.remove(hybridsGroupModel);
            customerModel.setGroups(newGroups);
        }

    }

    @Required
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
