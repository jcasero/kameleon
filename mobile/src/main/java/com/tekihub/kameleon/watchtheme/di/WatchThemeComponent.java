package com.tekihub.kameleon.watchtheme.di;

import com.tekihub.kameleon.watchtheme.AppThemeService;
import com.tekihub.kameleon.di.ApplicationComponent;
import com.tekihub.kameleon.watchtheme.usage.di.UsageModule;
import com.tekihub.kameleon.watchtheme.communication.di.CommunicationModule;
import com.tekihub.kameleon.watchtheme.theme.di.ThemeModule;

import dagger.Component;

@WatchThemeScope
@Component(dependencies = ApplicationComponent.class, modules = {UsageModule.class, CommunicationModule.class, ThemeModule.class})
public interface WatchThemeComponent {
    void inject(AppThemeService appThemeService);
}
