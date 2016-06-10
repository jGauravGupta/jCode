package ${package};

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.authentication.mechanism.http.HttpMessageContext;
import javax.security.authentication.mechanism.http.annotation.AutoApplySession;
import javax.security.identitystore.CredentialValidationResult;
import static javax.security.identitystore.CredentialValidationResult.Status.VALID;
import javax.security.identitystore.IdentityStore;
import javax.security.identitystore.credential.Password;
import javax.security.identitystore.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AutoApplySession
@RequestScoped
public class AuthenticationMechanismHandler implements HttpAuthenticationMechanism {

    @Inject
    private IdentityStore identityStore;

    @Override
    public AuthStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthException {
        String name = request.getParameter("name");
        if (name != null && request.getParameter("password") != null) {

            Password password = new Password(request.getParameter("password"));

            CredentialValidationResult result = identityStore.validate(new UsernamePasswordCredential(name, password));

            if (result.getStatus() == VALID) {
                return httpMessageContext.notifyContainerAboutLogin(result.getCallerPrincipal(), result.getCallerGroups());
            } else {
                return httpMessageContext.responseUnAuthorized();
            }
        }

        return httpMessageContext.doNothing();
    }

}
