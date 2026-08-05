package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.beta_pause_menu;

import io.github.poeticrainbow.retrotweaks.mixin.client.required.ScreenAccessor;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PauseScreen.class)
public class PauseScreenMixin extends Screen {
    protected PauseScreenMixin(Component component, boolean showPauseMenu) {
        super(component);
        this.showPauseMenu = showPauseMenu;
    }

    @Shadow private final boolean showPauseMenu;

    @Inject(method = "init", at = @At("TAIL"))
    private void retrotweaks$adjust_button_positions(CallbackInfo ci) {
        if (Tweaks.BETA_PAUSE_MENU.get()) {
            List<Renderable> renderables = ((ScreenAccessor) this).retrotweaks$getRenderables();

            for (Object obj : renderables) {
                if (obj instanceof AbstractWidget widget) {
                    String msg = widget.getMessage().getString();

                    if (msg.equals(Component.translatable("menu.returnToGame").getString())) {
                        widget.setX(this.width / 2 - 100);
                        widget.setY(this.height / 4 + 24 - 16);
                        widget.setHeight(20);
                        widget.setWidth(200);
                    } else if (msg.equals(Component.translatable("menu.options").getString())) {
                        widget.setX(this.width / 2 - 100);
                        widget.setY(this.height / 4 + 96 - 16);
                        widget.setHeight(20);
                        widget.setWidth(200);
                    } else if (msg.equals(Component.translatable("gui.advancements").getString())) {
                        widget.setX(this.width / 2 - 100);
                        widget.setY(this.height / 4 + 48 - 16);

                        widget.setHeight(20);
                        widget.setWidth(98);
                    } else if (msg.equals(Component.translatable("gui.stats").getString())) {
                        widget.setX(this.width / 2 + 2);
                        widget.setY(this.height / 4 + 48 - 16);
                        widget.setHeight(20);
                        widget.setWidth(98);
                    } else if (msg.equals(CommonComponents.disconnectButtonLabel(this.minecraft.isLocalServer()).getString())) {
                        widget.setX(this.width / 2 - 100);
                        widget.setY(this.height / 4 + 120 - 16);
                        widget.setHeight(20);
                        widget.setWidth(200);
                    } else {
                        widget.visible = false;
                        widget.active = false;
                        widget.setX(-1000);
                    }
                }
            }
            int i = this.font.width(this.title);

            this.addRenderableWidget(new StringWidget(this.width / 2 - i / 2, 40, i, 9, this.title, this.font));
        }
    }
}
