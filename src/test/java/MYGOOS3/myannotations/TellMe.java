package MYGOOS3.myannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/*
* This annotation is designed for checking which method is being used 
* in order to show failed tests clearly and readably.
*/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface TellMe {

}