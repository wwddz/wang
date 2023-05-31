package com.yzq.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;


@Aspect
public class OnlyOneUser {
  @Before(value = "execution(* com.yzq.controller.UserController.login(..))")
  public void isExit() {

  }
}
