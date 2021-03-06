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

package me.shedaniel.rei.impl.widgets;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.REIHelper;
import me.shedaniel.rei.api.widgets.Arrow;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class ArrowWidget extends Arrow {
    @NotNull
    private Rectangle bounds;
    private double animationDuration = -1;
    
    public ArrowWidget(@NotNull me.shedaniel.math.Rectangle bounds) {
        this.bounds = new Rectangle(Objects.requireNonNull(bounds));
    }
    
    @Override
    public double getAnimationDuration() {
        return animationDuration;
    }
    
    @Override
    public void setAnimationDuration(double animationDurationMS) {
        this.animationDuration = animationDurationMS;
        if (this.animationDuration <= 0)
            this.animationDuration = -1;
    }
    
    @NotNull
    @Override
    public Rectangle getBounds() {
        return bounds;
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(REIHelper.getInstance().getDefaultDisplayTexture());
        drawTexture(matrices, getX(), getY(), 106, 91, 24, 17);
        if (getAnimationDuration() > 0) {
            int width = MathHelper.ceil((System.currentTimeMillis() / (animationDuration / 24) % 24d) / 1f);
            drawTexture(matrices, getX(), getY(), 82, 91, width, 17);
        }
    }
    
    @Override
    public List<? extends Element> children() {
        return Collections.emptyList();
    }
}
