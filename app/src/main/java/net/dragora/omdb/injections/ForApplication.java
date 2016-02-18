package net.dragora.omdb.injections;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by nietzsche on 18/02/16.
 */
@Qualifier
@Retention(RUNTIME)
public @interface ForApplication {

}