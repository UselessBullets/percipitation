package useless.precipitation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WeatherMod {
    public static final String MOD_ID = "precipitation";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static void renderClouds(float lastOffX, float lastOffZ, float offX, float offZ, float partialTick) {
		Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
		GL11.glDisable(2884);
		float cameraY = (float)mc.activeCamera.getY(partialTick);
		int cloudRadius = 32;
		int i = 256 / cloudRadius;
		Tessellator tessellator = Tessellator.instance;
		GL11.glBindTexture(3553, mc.renderEngine.getTexture("/environment/clouds.png"));
		GL11.glEnable(3042);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Vec3d dimensionColor = mc.theWorld.getDimensionColor(partialTick);
		float r = (float)dimensionColor.xCoord;
		float g = (float)dimensionColor.yCoord;
		float b = (float)dimensionColor.zCoord;
		float f6 = 4.8828125E-4f;
		double posX = mc.activeCamera.getX(partialTick) + (double)((lastOffX + (offX - lastOffX) * partialTick) * 0.03f);
		double posZ = mc.activeCamera.getZ(partialTick) + (double)((lastOffZ + (offZ - lastOffZ) * partialTick) * 0.03f);
		int j = MathHelper.floor_double(posX / 2048.0);
		int k = MathHelper.floor_double(posZ / 2048.0);
		float cloudHeight = mc.theWorld.worldType.getCloudHeight() - cameraY + 0.33f;
		float f10 = (float)((posX - (j * 2048)) * (double)f6);
		float f11 = (float)((posZ - (k * 2048)) * (double)f6);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0, 1, 0);
		int iterations = 200;
		int half = iterations/2;
		for (int l = iterations; l > 0; l--) {
			cloudHeight += 0.1F;
			tessellator.setColorRGBA_F(r, g, b, 0.3f * (1f - (Math.abs(l - half)/((float)half))));
			for (int cloudX = -cloudRadius * i; cloudX < cloudRadius * i; cloudX += cloudRadius) {
				for (int cloudZ = -cloudRadius * i; cloudZ < cloudRadius * i; cloudZ += cloudRadius) {
					tessellator.addVertexWithUV(cloudX, cloudHeight, cloudZ + cloudRadius, (float)(cloudX) * f6 + f10, (float)(cloudZ + cloudRadius) * f6 + f11);
					tessellator.addVertexWithUV(cloudX + cloudRadius, cloudHeight, cloudZ + cloudRadius, (float)(cloudX + cloudRadius) * f6 + f10, (float)(cloudZ + cloudRadius) * f6 + f11);
					tessellator.addVertexWithUV(cloudX + cloudRadius, cloudHeight, cloudZ, (float)(cloudX + cloudRadius) * f6 + f10, (float)(cloudZ) * f6 + f11);
					tessellator.addVertexWithUV(cloudX, cloudHeight, cloudZ, (float)(cloudX) * f6 + f10, (float)(cloudZ) * f6 + f11);
				}
			}
		}

		tessellator.draw();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glDisable(3042);
		GL11.glEnable(2884);
	}
}
