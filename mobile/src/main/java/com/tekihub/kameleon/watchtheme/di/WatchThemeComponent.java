package com.tekihub.kameleon.watchtheme.di;

import com.tekihub.kameleon.KameleonService;
import com.tekihub.kameleon.di.ApplicationComponent;
import com.tekihub.kameleon.watchtheme.communication.di.CommunicationModule;
import com.tekihub.kameleon.watchtheme.theme.di.ThemeModule;
import com.tekihub.kameleon.watchtheme.usage.di.UsageModule;

import dagger.Component;

@WatchThemeScope
@Component(dependencies = ApplicationComponent.class, modules = {WatchThemeModule.class, UsageModule.class, CommunicationModule.class, ThemeModule.class})
public interface WatchThemeComponent {
    void inject(KameleonService kameleonService);
}
