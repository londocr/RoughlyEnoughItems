/*
 * This file is licensed under the MIT License, part of Roughly Enough Items.
 * Copyright (c) 2018, 2019, 2020 shedaniel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.shedaniel.rei.gui.plugin;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.*;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import me.shedaniel.rei.api.widgets.Panel;
import me.shedaniel.rei.api.widgets.Tooltip;
import me.shedaniel.rei.gui.RecipeViewingScreen;
import me.shedaniel.rei.gui.VillagerRecipeViewingScreen;
import me.shedaniel.rei.impl.ClientHelperImpl;
import me.shedaniel.rei.impl.RenderingEntry;
import me.shedaniel.rei.plugin.autocrafting.DefaultCategoryHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

@ApiStatus.Internal
@Environment(EnvType.CLIENT)
public class DefaultRuntimePlugin implements REIPluginV0 {
    public static final Identifier PLUGIN = new Identifier("roughlyenoughitems", "default_runtime_plugin");
    
    @Override
    public Identifier getPluginIdentifier() {
        return PLUGIN;
    }
    
    @Override
    public void registerEntries(EntryRegistry entryRegistry) {
        entryRegistry.registerEntry(new RenderingEntry() {
            private Identifier id = new Identifier("roughlyenoughitems", "textures/gui/kirb.png");
            
            @Override
            public void render(MatrixStack matrices, Rectangle bounds, int mouseX, int mouseY, float delta) {
                MinecraftClient.getInstance().getTextureManager().bindTexture(id);
                drawTexturedQuad(matrices.peek().getModel(), bounds.x, bounds.getMaxX(), bounds.y, bounds.getMaxY(), getZOffset(), 0, 1, 0, 1);
            }
            
            @Override
            public boolean isEmpty() {
                return !((ClientHelperImpl) ClientHelper.getInstance()).isAprilFools.get();
            }
            
            @Override
            public @Nullable Tooltip getTooltip(Point point) {
                return Tooltip.create(new LiteralText("Kibby"));
            }
        });
    }
    
    @Override
    public void registerBounds(DisplayHelper displayHelper) {
        BaseBoundsHandler baseBoundsHandler = BaseBoundsHandler.getInstance();
        baseBoundsHandler.registerExclusionZones(RecipeViewingScreen.class, () -> {
            Panel widget = ((RecipeViewingScreen) MinecraftClient.getInstance().currentScreen).getWorkingStationsBaseWidget();
            if (widget == null)
                return Collections.emptyList();
            return Collections.singletonList(widget.getBounds().clone());
        });
        displayHelper.registerProvider(new DisplayHelper.DisplayBoundsProvider<RecipeViewingScreen>() {
            @Override
            public Rectangle getScreenBounds(RecipeViewingScreen screen) {
                return screen.getBounds();
            }
            
            @Override
            public Class<?> getBaseSupportedClass() {
                return RecipeViewingScreen.class;
            }
        });
        displayHelper.registerProvider(new DisplayHelper.DisplayBoundsProvider<VillagerRecipeViewingScreen>() {
            @Override
            public Rectangle getScreenBounds(VillagerRecipeViewingScreen screen) {
                return screen.bounds;
            }
            
            @Override
            public Class<?> getBaseSupportedClass() {
                return VillagerRecipeViewingScreen.class;
            }
        });
    }
    
    @Override
    public void registerOthers(RecipeHelper recipeHelper) {
        recipeHelper.registerAutoCraftingHandler(new DefaultCategoryHandler());
    }
}
