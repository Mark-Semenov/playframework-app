package controllers;

import entity.User;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import service.UserService;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class UserController extends Controller {

    private final UserService userService;
    private final HttpExecutionContext httpExecutionContext;

    private final FormFactory formFactory;

    @Inject
    public UserController(UserService userService, HttpExecutionContext httpExecutionContext, FormFactory formFactory) {
        this.userService = userService;
        this.httpExecutionContext = httpExecutionContext;
        this.formFactory = formFactory;
    }

    public CompletionStage<Result> list() {
        return getUsers().thenApplyAsync(
                resp -> ok(Json.toJson(List.of(resp))),
                httpExecutionContext.current()
        );
    }

    private CompletionStage<List<User>> getUsers() {
        return CompletableFuture.completedFuture(userService.getUsers());
    }


    public Result user(Long id) {
        return ok(views.html.user.render(userService.getUserById(id)));
    }

    public Result hello(String name) {
        return ok(views.html.hello.render(name, userService.getUsers()));
    }


    public Result newUser(Http.Request request) {
        DynamicForm requestData = formFactory.form().bindFromRequest(request);
        String firstname = requestData.get("firstname");
        String lastname = requestData.get("lastname");
        User usr = new User(firstname, lastname);
        userService.registerUser(usr);
        return redirect("hello?name=" + usr.getName());
    }


    @Security.Authenticated
    public Result authenticatedCachedIndex() {
        return ok("It's secured!");
    }
}
