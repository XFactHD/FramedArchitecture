package xfacthd.framedarchitecture.client.model;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import xfacthd.framedarchitecture.common.FAContent;
import xfacthd.framedarchitecture.common.data.GratePosition;
import xfacthd.framedarchitecture.common.data.PropertyHolder;
import xfacthd.framedblocks.api.model.FramedBlockModel;
import xfacthd.framedblocks.api.util.client.BakedQuadTransformer;
import xfacthd.framedblocks.api.util.client.ModelUtils;

import java.util.List;
import java.util.Map;

public class FramedGrateModel extends FramedBlockModel
{
    private static final float PIXEL = 1F/16F;

    private final Direction.Axis axis;
    private final GratePosition position;
    private final boolean bordered;
    private final int baseOff;

    private FramedGrateModel(BlockState state, BakedModel baseModel, boolean bordered)
    {
        super(state, baseModel);
        this.axis = state.getValue(BlockStateProperties.AXIS);
        this.position = state.getValue(PropertyHolder.GRATE_POSITION);
        this.bordered = bordered;
        this.baseOff = bordered ? 1 : 0;
    }

    @Override
    @SuppressWarnings("SuspiciousNameCombination")
    protected void transformQuad(Map<Direction, List<BakedQuad>> quadMap, BakedQuad quad)
    {
        Direction.Axis quadAxis = quad.getDirection().getAxis();

        if (quadAxis == axis)
        {
            List<BakedQuad> dest = quadMap.get(isAgainstEdge(quad) ? quad.getDirection() : null);
            float offset = getQuadOffset(quad);

            for (int x = baseOff; x < 16 - baseOff; x++)
            {
                for (int z = baseOff; z < 16 - baseOff; z++)
                {
                    if ((x % 2 == 0) != (z % 2 == 0)) { continue; }

                    float minX = x * PIXEL;
                    float minZ = z * PIXEL;
                    float maxX = minX + PIXEL;
                    float maxZ = minZ + PIXEL;

                    BakedQuad copy = ModelUtils.duplicateQuad(quad);
                    if (quadAxis == Direction.Axis.Y && BakedQuadTransformer.createTopBottomQuad(copy, minX, minZ, maxX, maxZ))
                    {
                        BakedQuadTransformer.setQuadPosInFacingDir(copy, offset);
                        dest.add(copy);
                    }
                    else if (quadAxis != Direction.Axis.Y && BakedQuadTransformer.createSideQuad(copy, minX, minZ, maxX, maxZ))
                    {
                        BakedQuadTransformer.setQuadPosInFacingDir(copy, offset);
                        dest.add(copy);
                    }
                }
            }

            if (bordered)
            {
                BakedQuad copy = ModelUtils.duplicateQuad(quad);
                if (quadAxis == Direction.Axis.Y && BakedQuadTransformer.createTopBottomQuad(copy, 0, 0, PIXEL, 1))
                {
                    BakedQuadTransformer.setQuadPosInFacingDir(copy, offset);
                    dest.add(copy);
                }
                else if (quadAxis != Direction.Axis.Y && BakedQuadTransformer.createSideQuad(copy, 0, 0, PIXEL, 1))
                {
                    BakedQuadTransformer.setQuadPosInFacingDir(copy, offset);
                    dest.add(copy);
                }

                copy = ModelUtils.duplicateQuad(quad);
                if (quadAxis == Direction.Axis.Y && BakedQuadTransformer.createTopBottomQuad(copy, 1F - PIXEL, 0, 1, 1))
                {
                    BakedQuadTransformer.setQuadPosInFacingDir(copy, offset);
                    dest.add(copy);
                }
                else if (quadAxis != Direction.Axis.Y && BakedQuadTransformer.createSideQuad(copy, 1F - PIXEL, 0, 1, 1))
                {
                    BakedQuadTransformer.setQuadPosInFacingDir(copy, offset);
                    dest.add(copy);
                }

                copy = ModelUtils.duplicateQuad(quad);
                if (quadAxis == Direction.Axis.Y && BakedQuadTransformer.createTopBottomQuad(copy, PIXEL, 0, 1F - PIXEL, PIXEL))
                {
                    BakedQuadTransformer.setQuadPosInFacingDir(copy, offset);
                    dest.add(copy);
                }
                else if (quadAxis != Direction.Axis.Y && BakedQuadTransformer.createSideQuad(copy, PIXEL, 0, 1F - PIXEL, PIXEL))
                {
                    BakedQuadTransformer.setQuadPosInFacingDir(copy, offset);
                    dest.add(copy);
                }

                copy = ModelUtils.duplicateQuad(quad);
                if (quadAxis == Direction.Axis.Y && BakedQuadTransformer.createTopBottomQuad(copy, PIXEL, 1F - PIXEL, 1F - PIXEL, 1))
                {
                    BakedQuadTransformer.setQuadPosInFacingDir(copy, offset);
                    dest.add(copy);
                }
                else if (quadAxis != Direction.Axis.Y && BakedQuadTransformer.createSideQuad(copy, PIXEL, 1F - PIXEL, 1F - PIXEL, 1))
                {
                    BakedQuadTransformer.setQuadPosInFacingDir(copy, offset);
                    dest.add(copy);
                }
            }
        }
        else
        {
            float minCut = 2F/16F;
            float maxCut = 1F;
            if (position == GratePosition.CENTER)
            {
                minCut = 9F/16F;
                maxCut = 9F/16F;
            }
            else if (position == GratePosition.POSITIVE)
            {
                minCut = 1F;
                maxCut = 2F/16F;
            }

            int max = bordered ? 15 : 17;
            for (int x = baseOff; x < max; x++)
            {
                for (int z = 1; z < max; z++)
                {
                    if ((x % 2 == 0) != (z % 2 == 0) == (quad.getDirection().getAxisDirection() == Direction.AxisDirection.NEGATIVE)) { continue; }

                    float minX = x * PIXEL;
                    float minZ = z * PIXEL;
                    float maxX = minX + PIXEL;

                    BakedQuad copy = ModelUtils.duplicateQuad(quad);
                    if (quadAxis == Direction.Axis.Y)
                    {
                        boolean zAxis = axis == Direction.Axis.Z;
                        if (BakedQuadTransformer.createTopBottomQuad(copy, zAxis ? minX : 1F - maxCut, zAxis ? 1F - maxCut : minX, zAxis ? maxX : minCut, zAxis ? minCut : maxX))
                        {
                            BakedQuadTransformer.setQuadPosInFacingDir(copy, minZ);
                            quadMap.get(minZ == 1F ? quad.getDirection() : null).add(copy);
                        }
                    }
                    else if (axis == Direction.Axis.Y)
                    {
                        if (BakedQuadTransformer.createSideQuad(copy, minX, 1F - maxCut, maxX, minCut))
                        {
                            BakedQuadTransformer.setQuadPosInFacingDir(copy, minZ);
                            quadMap.get(minZ == 1F ? quad.getDirection() : null).add(copy);
                        }
                    }
                    else if (BakedQuadTransformer.createSideQuad(copy, 1F - maxCut, minX, minCut, maxX))
                    {
                        BakedQuadTransformer.setQuadPosInFacingDir(copy, minZ);
                        quadMap.get(minZ == 1F ? quad.getDirection() : null).add(copy);
                    }
                }
            }

            if (bordered)
            {
                BakedQuad copy = ModelUtils.duplicateQuad(quad);
                if (quadAxis == Direction.Axis.Y)
                {
                    if (BakedQuadTransformer.createTopBottomQuad(copy, Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE), minCut) &&
                        BakedQuadTransformer.createTopBottomQuad(copy, Direction.fromAxisAndDirection(axis, Direction.AxisDirection.NEGATIVE), maxCut)
                    )
                    {
                        quadMap.get(quad.getDirection()).add(copy);
                    }
                }
                else if (axis == Direction.Axis.Y)
                {
                    if (BakedQuadTransformer.createHorizontalSideQuad(copy, false, minCut) &&
                        BakedQuadTransformer.createHorizontalSideQuad(copy, true, maxCut)
                    )
                    {
                        quadMap.get(quad.getDirection()).add(copy);
                    }
                }
                else if (BakedQuadTransformer.createVerticalSideQuad(copy, Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE), minCut) &&
                         BakedQuadTransformer.createVerticalSideQuad(copy, Direction.fromAxisAndDirection(axis, Direction.AxisDirection.NEGATIVE), maxCut)
                )
                {
                    quadMap.get(quad.getDirection()).add(copy);
                }
            }
        }
    }



    private boolean isAgainstEdge(BakedQuad quad)
    {
        if (position == GratePosition.CENTER) { return false; }

        if (position == GratePosition.NEGATIVE)
        {
            return quad.getDirection().getAxisDirection() == Direction.AxisDirection.NEGATIVE;
        }
        return quad.getDirection().getAxisDirection() == Direction.AxisDirection.POSITIVE;
    }

    private float getQuadOffset(BakedQuad quad)
    {
        if (position == GratePosition.CENTER) { return 9F/16F; }

        boolean positivePos = position == GratePosition.POSITIVE;
        boolean positiveFace = quad.getDirection().getAxisDirection() == Direction.AxisDirection.POSITIVE;

        return (positivePos == positiveFace) ? 1F : (2F/16F);
    }



    public static FramedGrateModel normal(BlockState state, BakedModel baseModel)
    {
        return new FramedGrateModel(state, baseModel, false);
    }

    public static FramedGrateModel normal(BakedModel baseModel)
    {
        return new FramedGrateModel(FAContent.blockFramedGrate.get().defaultBlockState(), baseModel, false);
    }

    public static FramedGrateModel bordered(BlockState state, BakedModel baseModel)
    {
        return new FramedGrateModel(state, baseModel, true);
    }

    public static FramedGrateModel bordered(BakedModel baseModel)
    {
        return new FramedGrateModel(FAContent.blockFramedBorderedGrate.get().defaultBlockState(), baseModel, true);
    }
}
