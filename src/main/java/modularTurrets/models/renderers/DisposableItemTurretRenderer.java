package modularTurrets.models.renderers;

import modularTurrets.ModInfo;
import modularTurrets.models.ModelDamageAmp;
import modularTurrets.models.ModelDisposableItemTurret;
import modularTurrets.models.ModelRedstoneReactor;
import modularTurrets.models.SolarPanelAddon;
import modularTurrets.tileentity.turrets.DisposableItemTurretTileEntity;
import modularTurrets.tileentity.turrets.TurretHeadUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DisposableItemTurretRenderer extends TileEntitySpecialRenderer {

    private ModelDisposableItemTurret model;
    SolarPanelAddon solar;
    ModelDamageAmp amp;
    ModelRedstoneReactor reac;
    DisposableItemTurretTileEntity turretHead;
    ResourceLocation textures;

    public DisposableItemTurretRenderer() {
        model = new ModelDisposableItemTurret();
        solar = new SolarPanelAddon();
        amp = new ModelDamageAmp();
        reac = new ModelRedstoneReactor();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
        turretHead = (DisposableItemTurretTileEntity) te;

        int rotation = 0;
        if (te.getWorldObj() != null) {
            rotation = te.getBlockMetadata();
        }

        this.model.setRotationForTarget(turretHead.rotationXY, turretHead.rotationXZ);
        textures = (new ResourceLocation(ModInfo.ID + ":textures/blocks/disposableItemTurret.png"));
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(1.0F, -1F, -1F);
        GL11.glRotatef(rotation * 90, 0.0F, 1.0F, 0.0F);

        model.Base.rotateAngleX = turretHead.baseFitRotationX;
        model.Base.rotateAngleY = turretHead.baseFitRotationZ;
        model.Pole.rotateAngleX = turretHead.baseFitRotationX;
        model.Pole.rotateAngleY = turretHead.baseFitRotationZ;
        model.BoxUnder.rotateAngleX = turretHead.baseFitRotationX;
        model.renderAll();

        if (turretHead.base != null) {
            if (TurretHeadUtils.hasSolarPanelAddon(turretHead.base)) {
                ResourceLocation texturesSolar = (new ResourceLocation(
                    ModInfo.ID + ":textures/blocks/solarPanelAddon.png"));
                Minecraft.getMinecraft().renderEngine
                    .bindTexture(texturesSolar);
                solar.setRotationForTarget(turretHead.rotationXY,
                    turretHead.rotationXZ);
                solar.renderAll();
            }

            if (TurretHeadUtils.hasDamageAmpAddon(turretHead.base)) {
                ResourceLocation texturesAmp = (new ResourceLocation(ModInfo.ID
                    + ":textures/blocks/damageAmpAddon.png"));
                Minecraft.getMinecraft().renderEngine.bindTexture(texturesAmp);
                amp.setRotationForTarget(turretHead.rotationXY,
                    turretHead.rotationXZ);
                amp.renderAll();
            }

            if (TurretHeadUtils.hasRedstoneReactor(turretHead.base)) {
                ResourceLocation texturesReac = (new ResourceLocation(
                    ModInfo.ID + ":textures/blocks/redstoneReactor.png"));
                Minecraft.getMinecraft().renderEngine.bindTexture(texturesReac);
                reac.setRotationForTarget(turretHead.rotationXY,
                    turretHead.rotationXZ);
                reac.renderAll();
            }
        }

        GL11.glPopMatrix();
    }

}