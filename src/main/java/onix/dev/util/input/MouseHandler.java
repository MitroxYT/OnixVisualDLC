package onix.dev.util.input;

import net.minecraft.client.Minecraft;
import onix.dev.Onixvisual;
import onix.dev.ui.clickgui.ClickGuiScreen;
import org.lwjgl.glfw.GLFW;

public class MouseHandler {
    
    private static boolean leftPressed = false;
    private static boolean rightPressed = false;
    private static double lastMouseX = 0;
    private static double lastMouseY = 0;
    
    public static void handleMouse() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.getWindow() == null) return;
        
        ClickGuiScreen clickGui = Onixvisual.getInstance().getFunctionManager().getModule(ClickGuiScreen.class);
        if (clickGui == null || !clickGui.isOpen()) return;
        
        long window = mc.getWindow().handle();
        double mouseX = 0;
        double mouseY = 0;
        
        try {
            double[] xpos = new double[1];
            double[] ypos = new double[1];
            GLFW.glfwGetCursorPos(window, xpos, ypos);
            

            mouseX = xpos[0] * mc.getWindow().getGuiScaledWidth() / mc.getWindow().getWidth();
            mouseY = ypos[0] * mc.getWindow().getGuiScaledHeight() / mc.getWindow().getHeight();
        } catch (Exception e) {
            return;
        }
        
        boolean leftDown = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
        boolean rightDown = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;
        
        if (leftDown && !leftPressed) {
            clickGui.handleMouseClick(mouseX, mouseY, 0);
            leftPressed = true;
        } else if (!leftDown && leftPressed) {
            clickGui.handleMouseRelease(mouseX, mouseY, 0);
            leftPressed = false;
        }
        
        if (rightDown && !rightPressed) {
            clickGui.handleMouseClick(mouseX, mouseY, 1);
            rightPressed = true;
        } else if (!rightDown && rightPressed) {
            clickGui.handleMouseRelease(mouseX, mouseY, 1);
            rightPressed = false;
        }
        
        if (leftPressed && (mouseX != lastMouseX || mouseY != lastMouseY)) {
            clickGui.handleMouseDrag(mouseX, mouseY, 0, mouseX - lastMouseX, mouseY - lastMouseY);
        }
        
        lastMouseX = mouseX;
        lastMouseY = mouseY;
    }
}