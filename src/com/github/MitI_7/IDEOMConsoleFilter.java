package com.github.MitI_7;

import com.intellij.execution.filters.ConsoleFilterProvider;
import com.intellij.execution.filters.Filter;
import com.intellij.openapi.project.Project;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;


public class IDEOMConsoleFilter implements ConsoleFilterProvider {
    public IDEOMConfig.State state;

    public IDEOMConsoleFilter() {
        state = IDEOMConfig.getInstance().state;
    }

    @NotNull
    @Override
    public Filter[] getDefaultFilters(@NotNull Project project) {
        // TODO: 正確には実行したときにここを通るわけじゃないんだけど，ほかに方法がないっぽい？？
        SoundSetting soundSetting;

        soundSetting = this.state.soundSetting.get(SoundSetting.RUN);
        if (soundSetting.useSound) {
            SoundPlayer.play(soundSetting.soundPath, soundSetting.soundVolume);
        }

        Filter filter = new IDEOMFilter();
        return new Filter[]{filter};
    }

    class IDEOMFilter implements Filter {
        private final String filter_pattern = state.soundSetting.get(SoundSetting.CONSOLEFILTER).consoleFilter;

        @Override
        public Result applyFilter(String textLine, int endPoint) {
            Pattern pattern = Pattern.compile(filter_pattern);

            // INFO: コンソール中にpatternが何回もでるとその度に再生しちゃう・・・
            if (pattern.matcher(textLine).find()) {
                SoundSetting soundSetting = state.soundSetting.get(SoundSetting.CONSOLEFILTER);
                if (soundSetting.useSound) {
                    SoundPlayer.play(soundSetting.soundPath, soundSetting.soundVolume);
                }
            }
            return null;
        }
    }
}