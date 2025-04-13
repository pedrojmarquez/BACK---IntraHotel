package com.fct.backfct.security.jwt;

import com.fct.backfct.domain.models.dao.IEmpleadosDao;
import com.fct.backfct.security.entity.Empleados;
import com.fct.backfct.security.entity.UsuarioPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {
    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    @Autowired
    private IEmpleadosDao empleadosDao;

    public String generateToken(Authentication authentication){
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", usuarioPrincipal.getIdEmpleado());
        claims.put("nombre", usuarioPrincipal.getNombre());
        claims.put("rol", usuarioPrincipal.getAuthorities());
        return Jwts.builder().setClaims(claims).setSubject(usuarioPrincipal.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getEmailFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException e){
            logger.error("token mal formado");
        }catch (UnsupportedJwtException e){
            logger.error("token no soportado");
        }catch (ExpiredJwtException e){
            logger.error("token expirado");
        }catch (IllegalArgumentException e){
            logger.error("token vacío");
        }catch (SignatureException e){
            logger.error("fail en la firma");
        }
        return false;
    }
    private String getToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer"))
            return header.replace("Bearer ", "");
        return null;
    }

    public Empleados getUserFromToken(HttpServletRequest request) {
        String token = this.getToken(request);

        Empleados u;
        Empleados user=null;

        try {
            Claims claims = this.getClaimsFromToken(token);
            System.out.println(claims.get("id"));
            Long id = ((Number)claims.get("id")).longValue();
            System.out.println("ID: "+id);
            user = empleadosDao.findById(id).orElse(null);
            System.out.println("user: "+user);
        } catch (Exception var5) {
            u = null;
        }

        return user;
    }


    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = (Claims)Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        } catch (Exception var4) {
            claims = null;
        }

        return claims;
    }


}
