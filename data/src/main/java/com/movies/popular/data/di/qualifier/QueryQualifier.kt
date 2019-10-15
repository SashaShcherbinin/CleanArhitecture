package com.movies.popular.data.di.qualifier

import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class QueryQualifier(val value: String = "")
