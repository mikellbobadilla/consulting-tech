# Spring Security

## Authentication

_SecurityFilterChain_ Cuando el usuario hace una peticion a un endpoint privado
_FilterSecurityInterceptor_ es el que indica una reques denegada por que no esta auntenticado
el cual dispara un AccessDeniedException. Como el usuario no esta autenticado se inicia un ExceptonTraslationFilter que es el que se encaga
de iniciar los pasos que se necesita para la autenticacion y redirige al usuario la la pagina de login
configurado (normalmente por defecto cuando importamos la dependencia), pero tambien podemos configurar una pagina de login personalizado.

### Funcion Interna de la Autenticacion.

Cuando el usuario y la contrasena es enviada al servidor, La clase _UsernamePasswordAuthenticationFilter_ autentica al usuario.

Cuando la clase _UsernamePasswordAuthenticationFilter_ crea un _UsernamePasswordAuthenticationToken_ el cual es una clase de tipo _Authentication_
el cual extrae el usuario y la contrasena desde la clase _HttpServletRequest_.

Acontinuacion la clase _UsernamePasswordAuthenticationToken_ es pasado dentro de la clase _AuthenticationManager_ para ser autenticado.
los detalles de la clase _AuthenticationManager_ debe definirse depende en como la informacion del usuario es almacenada.

- Si la Autentication Falla:
  - La clase _SecurityContextHolder_ se limpia
  - La clase _RememberMeService.loginFail_ es incovado (Normalmente no esta configurado por defecto, pero se puede activar)
  - La clase _AuthenticationFailureHandler_ es invocado.

- Si la Autenticacion es confirmada:
  - La clase _Authentication_ es seteada dentro de la clase _SecurityContextHolder_
  - Si esta configurado la clase _RememberMeService.loginSuccess_ es invocada
  - La clase _AuthenticationSuccessHandler_ es invocado. Normalmente es una clase _SimpleUrlAuthenticationSuccessHandler_ el cual no redirigira la la reques que hicimos anteriormente en la clase _ExceptionTranslationFilter_

## Configurando Spring Security

- Lo primero que hacemos es crear la clase SecurityConfig

```java
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
    // toda la configuracion necesaria
}
```

```java
// Normalmente cuando importamos spring security automaticamente cuando hagamos una peticion spring nos redirigira a la pagina de inicio
// pero podemos usar esta configuracion, aunque no cambie nada

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterchain(HttpSecurity http) {
        http
                .formLogin(withDefaults());
    }
}
```

Si queremos usar una pagina de login personalizada la configuracion que se necesita es esta:
```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterchain(HttpSecurity http) {
        http
                .formLogin(form -> form
                        .loginpage("/login")
                        .permitAll()
                );
                
    }
}
```

Cuando especificamos la pagina de login propia en spring security es necesario renderizar
la pagina usando un template como _Thymeleaf_ que se encarga de producir una login que es compilado en html
de "/login"

***Ejemplo de login***
```html
<!DOCTYPE html>
<html xmlns:="http://www.w3.org/1999/xtml" xmlns:th="https://www.tymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=.no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Login Page</title>
</head>
<body>
    <h1>Please login</h1>
    <!-- This will show if the params has an error -->
    <div th:if="${param.error}">
        Invalid username and password.
    </div>
    <div>
        You have been logged out.
    </div>
    <form th:action="@{/login}" method="post">
        <div>
            <input type="text" name="username" placeholder="Username"/>
        </div>
        <div>
            <input type="password" name="password" placeholder="Password" />
        </div>
        <input type="submit" value="Login" />
    </form>
</body>
</html>
```
- ***Algunos punto clave sobre el formulario HTML***
  - El formuladio deberia de ser enviado de tipo post al controlador /login (Internamente esta configurado en spring)
  - El formulario necesita incluir un CSRF token el cual es automaticamente incluido por Thymeleaf (en httpsecurity podemos desactivarlo)
  - El formulario los inputs necesitan el atributo name con sus respectivos valores como 'username' y 'password' porque la clase HttpServletRequest usa esos parametros para poder extraer los datos para poder autenticar al usuario
  - Lo demas incuido tambien es necesario de como esta gestionado internamente spring.

***Creando el controlador login***
primeo creamos la clase LoginController y agregamos lo siguiente:

```java
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
```
lo que retorna ese metodo con la notacion GetMappin tene que ser el mismo nombre que le dimos al formulario donde se creo el login
ese archivo html tiene que ser guardado en la carpeta _resources/templates_ ahi es donde Thymeleaf mapea todos los archivos html que tiene que renderizar.

## Arquitectura de la Autenticacion:

- ***SecurityContextHolder***: Es donde spring almacena todos los detalles de quien esta autenticado.
- ***SecurityContext***: Se obtiene de la clase ***SecurityContextHolder*** y contiene la autenticacion del usuario autenticado actualmente.
- ***Authentication***: puede ser la entrada al ***AuthenticationManager*** para proporcionar las credenciales que un usuario ha proporcionado para autenticarse o el usuario actual de la clase ***SecurityContext***
- ***GrantedAuthority***: Es una autoridad o varias que se otorga en la clase ***Authentication***.
- ***AuthenticationManager***: La API que define como los filtros de spring security realizan la autenticacion.
- ***ProviderManager***: La implmentacion mas comun de la clase ***AuthenticationManager***
- ***AuthenticationProvider***: Utilizado por ***ProvidesManager*** para realizar un tipo específico de autenticacion.
- ***Request Credential with AuthenticationEntryPoint***: Usado para solicitar credenciales al cliente()
- ***AbstractAuthenticationProcessingFilter***: Es un flujo base utilizado para la autenticacion. Esto tambien da una buena idea del flujo de alto nivel de la autenticacion y como las piezas trabajan juntas.

### SecurityContextHolder
Es el core de spring security se encuentra la clase ***SecurityContextHolder*** y contiene a la clase ***SecurityContext***
Es donde se almacena los detalles de quien está autenticado.

La forma más sencilla de que un usario está autenticado es configurar la clase ***SecurityContextHolder*** directamente
```java
SecurityContext context = SecurityContextHolder.createEmtyContext(); (1)
Authentication auth = new TestingAuthenticationToken("username", "password", "ROLE_USER"); (2)

Context.setAuthentication(auth); (3)

SecurityContextHolder.setContext(context); (4)
```

1. Creamos una variable de tipo ***SecurityContext*** vacio, es importante crear una nueva instancia en lugar de utilizar SecurtyContextHolder.getContext().setAuthentication(auth) para evitar conflictos de varios subprocesos.

2. Despues creamos un objeto de tipo Authentication, en este caso se esta usando TestingAuthenticationToken ya que es simple que se establece en SecurityContext. Normalmente en Produccion se utiliza UsernamePasswordAuthenticationToken(userDetail, password, authorities).

3. Luego el objeto context seteamos la auteticacion que hemos creado con ***context.setAuthentication(auth)***

4. Finalmente configuramos el SecurityContext en el objeto SecurityContextHolder en el que spring security utilizara esta informacion para la autorizacion.

#### si queremos obtener informacion del autenticado, se tiene que acceder al objeto SecurityContextHolder

***ejemplo:***
```java
SecurityContext context = SecurityContextHolder.getContext();
Authentication auth = context.getAuthentication();
String username = auth.getName();
Object principal = auth.getPrincipal();
Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
```
#### Autenticacion

El objeto Authentication tiene dos propositos principales dentro de spring security
- Una entrada al AuthenticationManager para proporcionar las credenciales que un usuario a proporcionado para autenticarse. Cuando se utiliza este escenario isAuthenticated() devuelve false.
- Representa al usuario autenticado actualmente. El objeto Authentication se obiene del SecurityContext.
- El objeto ***Authentication*** contiene:
    - ***principal***: identifica al usuario. Cuando se autentica con un nombre de usuario/contrasena, normalmente es una instancia de la clase ***UserDetails***.
    - ***credentials***: normalmente es una contrasena. En muchos casos esto se borrara despues de que el usuario se autentique para garantizar que no se filtre.
    - ***Authorities***: los ***GrantedAuthoritys*** son permisos de alto nivel que se otorgan al usuario. Algunos ejemplos son "ROLE_USER", "ROLE_ADMIN".

#### GrantedAuthority

GrantedAuthority son permisos de alto nivel que se otorgan al usuario.

Estos se pueden obtener del metodo ***Authentication.getAuthorities()***. Este metodo devuelve una coleccion de objetos GrantedAuthority. Estos Authorities suelen ser role como ROLE_USER, ROLE_ADMIN. 
Estos se configuran posteriormente para la autorizacion web.
Otras partes de Spring security son capaces de interpretar esta autorizaciones y se esperan que esten presentes. Cuando se utiliza la authentication basada en nombre/contrasena, los GrantedAuthority normalmente se cargan mediante el objeto UserDetailsService.

Estos son permisos para toda la aplicacion.

#### AuthenticationManager

AuthenticationManager es la API que define como los filtros de spring secrity realiza la autenticacion. 
La authentication que se establece en el SecurityContextHolder por el controlador(osea los filtros de spring security) que invoco el AuthenticationManager.
Si no lo estas integrando con Spring Security's Filters puedes establecer el objeto SecurityContextHolder directamente y no esta necesario usar un AuthenticationManager

Aunque la implementacion de AuthenticationManager puede ser cualquier cosa, la implementacion mas comun es PrividerManager.

#### ProviderManager

Es la implementacion mas utilizada de AuthenticationManager. ProviderManager delega de una lista de AuthenticationProviders. Cada AuthenticationProvider tiene la oportunidad de indicar que la autenticacion debe ser exitosa, fallar, o indicar que no puede tomar una decision y permitir que un AuthenticationProvider posterior decida. Si nunguno de los AuthenticationPrividers configurados para autenticar, entonces la autenticacion fallara con un ProviderNotFoundException que es una AuthenticationException especial que indica que el ProviderManager no fue configurado para soportar el tipo de autenticacion que se le paso.

En la practica, cada AuthenticationProvider sabe como realizar un tipo especifico de autenticacion. Por ejemplo un AuthenticationProvider podria ser capaz de validar un nombre de usuario/contrasena, mientras que otro podria ser capaz de una asercion SAML. Esto permite que cada AuthenticationProvider haga un tipo de autenticacion muy especifico, mientras soporta multiples tipos de autenticacion y solo expone un unico bean AuthenticationManager.

ProviderManager tambien permite configurar un AuthenticationManager padre opcional que se consulta en caso de que ningun AuthenticationProvider pueda realizar la autenticacion. Esto puede ser cualquier tipo de AuthenticationManagenr pero suele ser una instancia de ProviderManager.

De echo varias instancias de ProviderManager pueden compartir el mismo AuthenticationManager padre. Esto es algo comun en escenarios donde hay multiples instancias de SecurityFilterChain que tiene una autenticacion en comun (el AuthenticationManager padre compartido), pero tambien diferentes mecanismos de autenticacion (instancias de ProviderManger).


#### AuthenticationProvider

Se pueden inyectar varios AuthenticationProviders en el ProviderManager. Cada AuthenticationManager realiza un tipo especifico de authenticacion. 
Por ejemplo:
DaoAuthenticationProvider soportla la autenticacion basada en nombre de usuario/contrasena mientras que JwtAuthenticationProvider soporta la autenticacion de un token JWT.








