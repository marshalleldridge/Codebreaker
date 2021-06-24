package edu.cnm.deepdive.codebreaker;

import android.app.Application;
import com.facebook.stetho.Stetho;

public class CodebreakerApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    //TODO initialize database.
    //TODO initialize other services as necessary
    Stetho.initializeWithDefaults(this);
  }
}
