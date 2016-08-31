package cesar.spring.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

    public static String getUsername()
    {
        Authentication auth = SecurityContextHolder.getContext()
            .getAuthentication();
        return auth.getPrincipal() instanceof UserDetails ? ((UserDetails) auth
            .getPrincipal()).getUsername() : auth.getPrincipal().toString();
    }

    public static boolean isAnonymousUser()
    {
        return (new AuthenticationTrustResolverImpl())
            .isAnonymous( SecurityContextHolder.getContext()
                .getAuthentication() );
    }

    public static boolean isUserInRole( String roleName )
    {
        Authentication auth = SecurityContextHolder.getContext()
            .getAuthentication();

        if( !(auth.getPrincipal() instanceof UserDetails) ) return false;

        List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) auth
            .getAuthorities();
        for( GrantedAuthority grantedAuthority : grantedAuthorities )
            if( grantedAuthority.getAuthority().equals( roleName ) )
                return true;

        return false;

    }

    public static boolean isAdministrator()
    {
        return isUserInRole( "ROLE_STAFF" );
    }

}
