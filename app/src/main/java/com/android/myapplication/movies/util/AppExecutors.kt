package com.android.myapplication.movies.util

import java.util.concurrent.Executors

public class AppExecutors  {
     val networkIO = Executors.newScheduledThreadPool(3)
}