package onix.dev.ui.clickgui.component;

import onix.dev.module.api.Category;
import onix.dev.module.api.Function;
import onix.dev.util.render.animation.Easings;
import onix.dev.util.render.core.Renderer2D;
import onix.dev.util.render.text.FontObject;
import onix.dev.util.render.text.FontRegistry;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Panel {

    private final Category category;
    public float currentX, currentY;
    public float targetX;
    public float targetY;
    private final float width = 120;

    private boolean dragging = false;
    private float dragOffsetX, dragOffsetY;
    private boolean expanded = true;
    

    private float expandAnimation = 1.0f;
    private float targetExpandAnimation = 1.0f;

    private final List<ModuleButton> allButtons = new ArrayList<>();
    private final List<ModuleButton> generalButtons = new ArrayList<>();
    private final List<ModuleButton> visualButtons = new ArrayList<>();

    private boolean showVisuals = false;
    private boolean targetShowVisuals = false;
    

    private float switchAnimation = 0.0f;
    private float targetSwitchAnimation = 0.0f;

    private final float HEADER_HEIGHT = 24;
    private final float SUB_HEADER_HEIGHT = 18;
    private final float PADDING = 4;

    public Panel(Category category, float startX, float startY, List<Function> modules) {
        this.category = category;
        this.targetX = startX;
        this.targetY = startY;
        this.currentX = startX;
        this.currentY = startY;

        for (Function module : modules) {
            ModuleButton btn = new ModuleButton(module, PADDING, 0, width - PADDING * 2);
            if (category == Category.RENDER) {
                if (module.isVisual()) visualButtons.add(btn);
                else generalButtons.add(btn);
            } else {
                allButtons.add(btn);
            }
        }
    }

    private float getCurrentHeight() {
        if (!expanded) return HEADER_HEIGHT;

        float baseHeight = HEADER_HEIGHT + PADDING;
        if (category == Category.RENDER) baseHeight += SUB_HEADER_HEIGHT;

        List<ModuleButton> activeList;
        if (category == Category.RENDER) {
            activeList = showVisuals ? visualButtons : generalButtons;
        } else {
            activeList = allButtons;
        }

        float contentHeight = 0;
        for (ModuleButton btn : activeList) {
            contentHeight += btn.getHeight() + PADDING;
        }

        return baseHeight + contentHeight * expandAnimation;
    }
    
    private void updateAnimations() {

        if (expanded && targetExpandAnimation != 1.0f) {
            targetExpandAnimation = 1.0f;
        } else if (!expanded && targetExpandAnimation != 0.0f) {
            targetExpandAnimation = 0.0f;
        }
        
        expandAnimation = lerp(expandAnimation, targetExpandAnimation, 0.15f);
        

        if (category == Category.RENDER) {
            if (targetShowVisuals && targetSwitchAnimation != 1.0f) {
                targetSwitchAnimation = 1.0f;
            } else if (!targetShowVisuals && targetSwitchAnimation != 0.0f) {
                targetSwitchAnimation = 0.0f;
            }
            
            switchAnimation = lerp(switchAnimation, targetSwitchAnimation, 0.12f);
            

            if (Math.abs(switchAnimation - targetSwitchAnimation) < 0.1f) {
                showVisuals = targetShowVisuals;
            }
        }
    }

    private void updateButtonPositions(List<ModuleButton> list, float startY) {
        float cy = startY;
        for (ModuleButton btn : list) {
            btn.setLocalY(cy);
            cy += btn.getHeight() + PADDING;
        }
    }

    public void render(Renderer2D r, FontObject font, float alpha, float yAnimOffset, double mx, double my) {
        updateAnimations();
        
        if (dragging) {
            targetX = (float)mx - dragOffsetX;
            targetY = (float)my - dragOffsetY;
            currentX = targetX;
            currentY = targetY;
        } else {
            currentX = lerp(currentX, targetX, 0.25f);
            currentY = lerp(currentY, targetY, 0.25f);
        }

        float height = getCurrentHeight();
        float renderY = currentY + yAnimOffset;

        int bgAlpha = (int)(180 * alpha);
        int headAlpha = (int)(220 * alpha);
        int textAlpha = (int)(255 * alpha);


        r.rect(currentX, renderY, width, height, 6, new Color(15, 15, 20, bgAlpha).getRGB());

        r.rect(currentX, renderY, width, HEADER_HEIGHT, 0, 6, 6, 0, new Color(35, 35, 40, headAlpha).getRGB());
        r.text(FontRegistry.INTER_MEDIUM, currentX + width / 2, renderY + HEADER_HEIGHT / 2 + 3, 10, 
               category.getName(), new Color(255, 255, 255, textAlpha).getRGB(), "c");

        if (category == Category.RENDER && expanded) {
            renderAnimatedSwitcher(r, renderY, alpha);
            renderAnimatedContent(r, font, renderY, alpha, mx, my);
        } else if (expanded) {
            updateButtonPositions(allButtons, HEADER_HEIGHT + PADDING);
            renderButtonsWithAnimation(r, font, allButtons, renderY, alpha, mx, my);
        }
    }

    private void renderAnimatedSwitcher(Renderer2D r, float renderY, float alpha) {
        float y = renderY + HEADER_HEIGHT;
        float half = width / 2;
        int activeC = new Color(100, 160, 255, (int)(255 * alpha)).getRGB();
        int inactiveC = new Color(160, 160, 160, (int)(150 * alpha)).getRGB();


        r.rect(currentX, y, width, SUB_HEADER_HEIGHT, 0, new Color(25, 25, 30, (int)(100 * alpha)).getRGB());


        float indX = currentX + (half * switchAnimation);
        float indWidth = half;
        r.rect(indX, y + SUB_HEADER_HEIGHT - 2, indWidth, 2, activeC);

        float mainAlpha = 1.0f - switchAnimation;
        float visualAlpha = switchAnimation;
        
        int mainColor = interpolateColor(activeC, inactiveC, switchAnimation);
        int visualColor = interpolateColor(inactiveC, activeC, switchAnimation);

        r.text(FontRegistry.SF_REGULAR, currentX + half/2, y + 12, 8, "Main", mainColor, "c");
        r.text(FontRegistry.SF_REGULAR, currentX + half + half/2, y + 12, 8, "Visuals", visualColor, "c");
    }

    private void renderAnimatedContent(Renderer2D r, FontObject font, float renderY, float alpha, double mx, double my) {
        float contentY = renderY + HEADER_HEIGHT + SUB_HEADER_HEIGHT + PADDING;
        

        if (switchAnimation < 1.0f) {

            updateButtonPositions(generalButtons, HEADER_HEIGHT + SUB_HEADER_HEIGHT + PADDING);
            float mainAlpha = alpha * (1.0f - switchAnimation);
            float mainOffset = -20 * switchAnimation;
            renderButtonsWithAnimation(r, font, generalButtons, renderY + mainOffset, mainAlpha, mx, my);
        }
        
        if (switchAnimation > 0.0f) {

            updateButtonPositions(visualButtons, HEADER_HEIGHT + SUB_HEADER_HEIGHT + PADDING);
            float visualAlpha = alpha * switchAnimation;
            float visualOffset = 20 * (1.0f - switchAnimation);
            renderButtonsWithAnimation(r, font, visualButtons, renderY + visualOffset, visualAlpha, mx, my);
        }
    }

    private void renderButtonsWithAnimation(Renderer2D r, FontObject font, List<ModuleButton> buttons, 
                                          float renderY, float alpha, double mx, double my) {
        if (alpha <= 0.01f) return;
        
        for (int i = 0; i < buttons.size(); i++) {
            ModuleButton btn = buttons.get(i);
            

            btn.updateHover(mx, my, currentX, renderY);
            

            float buttonDelay = i * 0.05f;

            float buttonAlpha = 1.0f;
            if (category == Category.RENDER) {
                buttonAlpha = Math.min(1.0f, Math.max(0.0f, expandAnimation - buttonDelay));
                buttonAlpha = Easings.EASE_OUT_CUBIC.ease(buttonAlpha);
            }
            
            if (buttonAlpha > 0.01f) {


                float yOffset = (category == Category.RENDER) ? (1.0f - buttonAlpha) * 10 : 0;
                btn.render(r, font, currentX, renderY - yOffset, alpha * buttonAlpha);
            }
        }
    }

    public void mouseClicked(double mx, double my, int btn) {
        if (!isHovered(mx, my)) return;


        if (my >= currentY && my <= currentY + HEADER_HEIGHT) {
            if (btn == 0) {
                dragging = true;
                dragOffsetX = (float)mx - currentX;
                dragOffsetY = (float)my - currentY;
            } else if (btn == 1) {
                expanded = !expanded;
            }
            return;
        }


        if (category == Category.RENDER && expanded) {
            float sy = currentY + HEADER_HEIGHT;
            if (my >= sy && my <= sy + SUB_HEADER_HEIGHT) {
                boolean newShowVisuals = mx >= currentX + width / 2;
                if (newShowVisuals != targetShowVisuals) {
                    targetShowVisuals = newShowVisuals;
                }
                return;
            }


            List<ModuleButton> list = showVisuals ? visualButtons : generalButtons;
            for (ModuleButton mb : list) {
                if (mb.mouseClicked(mx, my, btn, currentX, currentY)) return;
            }
            return;
        }

        if (expanded) {
            for (ModuleButton mb : allButtons) {
                if (mb.mouseClicked(mx, my, btn, currentX, currentY)) return;
            }
        }
    }

    public void mouseDragged(double mx, double my, int btn, double dx, double dy) {
        if (expanded && !dragging) {
            List<ModuleButton> list = getActiveList();
            for (ModuleButton mb : list) mb.mouseDragged(mx, my, btn, currentX, currentY);
        }
    }

    public void mouseReleased(double mx, double my, int btn) {
        dragging = false;
        if (expanded) {
            List<ModuleButton> list = getActiveList();
            for (ModuleButton mb : list) mb.mouseReleased(mx, my, btn);
        }
    }
    public List<ModuleButton> getButtons() { return getActiveList(); }

    public Function getHoveredModule(double mx, double my) {
        if (!expanded) return null;
        for (ModuleButton mb : getActiveList()) {
            if (mx >= currentX + mb.getLocalX() && mx <= currentX + mb.getLocalX() + width &&
                    my >= currentY + mb.getLocalY() && my <= currentY + mb.getLocalY() + mb.getHeight()) {
                return mb.getModule();
            }
        }
        return null;
    }

    public List<ModuleButton> getActiveList() {
        if (category == Category.RENDER) {
            return showVisuals ? visualButtons : generalButtons;
        }
        return allButtons;
    }

    private boolean isHovered(double mx, double my) {
        return mx >= currentX && mx <= currentX + width && my >= currentY && my <= currentY + getCurrentHeight();
    }

    private float lerp(float s, float e, float d) { 
        return s + (e - s) * d; 
    }
    
    private int interpolateColor(int color1, int color2, float factor) {
        factor = Math.max(0f, Math.min(1f, factor));
        
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = color1 & 0xFF;
        int a1 = (color1 >> 24) & 0xFF;
        
        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = color2 & 0xFF;
        int a2 = (color2 >> 24) & 0xFF;
        
        int r = (int) (r1 + (r2 - r1) * factor);
        int g = (int) (g1 + (g2 - g1) * factor);
        int b = (int) (b1 + (b2 - b1) * factor);
        int a = (int) (a1 + (a2 - a1) * factor);
        
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}