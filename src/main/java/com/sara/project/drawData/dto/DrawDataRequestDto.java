package com.sara.project.drawData.dto;

import com.sara.project.dcp.Dcp;
import com.sara.project.drawData.DrawData;
import com.sara.project.drawData.Shadow;
import com.sara.project.gateway.TbGateway;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.math.BigDecimal;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DrawDataRequestDto {

    private Long id;
    private String type; // string
    private String version; // string
    private String originX; // String
    private String originY; //String
    private BigDecimal left; //
    private BigDecimal top;
    private BigDecimal width;
    private BigDecimal height;
    private String fill;
    private String stroke;
    private Integer strokeWidth;
    private String strokeDashArray;
    private String strokeLineCap;
    private Integer strokeDashOffset;
    private String strokeLineJoin;
    private boolean strokeUniform;
    private Integer strokeMiterLimit;
    private BigDecimal scaleX;
    private BigDecimal scaleY;
    private Integer angle;
    private boolean flipX;
    private boolean flipY;
    private Integer opacity;
    private Shadow shadow;
    private boolean visible;
    private String backgroundColor;
    private String fillRule;
    private String paintFirst;
    private String globalCompositeOperation;
    private Integer skewX;
    private Integer skewY;
    private Long typeId;
    private String offMsg;
    private String notMsg;
    private String notImg;
    private String offImg;
    private boolean erasable;
    private Integer cropX;
    private Integer cropY;
    private String src;
    private Integer crossOrigin;
    private Long groupId;
    //private String filters;
    /*새로 추가*/

    private Integer rx;
    private Integer ry;
    private boolean splitByGrapheme;
    private Integer charSpacing;
    private String direction;
    private String fontFamily;
    private BigDecimal fontSize;
    private String fontStyle;
    private String fontWeight;
    private BigDecimal lineHeight;
    private boolean linethrough;
    private BigDecimal minWidth;
    private boolean overline;
    private String pathSide;
    private Integer pathStartOffset;
    private String text;
    private String textAlign;
    private String textBackgroundColor;
    private boolean underline;

    private String gatewayId;
    private String machineType;
    private String machineAddr;
    private String didoType;
    private String inOutNum;

    private String offFill;
    private String notFill;
    private String offBackColor;
    private String notBackColor;

    public DrawData of(
            Long id
    ) {
        return DrawData.builder()
                .id(id)
                .type(this.type)
                .version(this.version)
                .originX(this.originX)
                .originY(this.originY)
                .leftSize(this.left)
                .top(this.top)
                .width(this.width)
                .height(this.height)
                .fill(this.fill)
                .stroke(this.stroke)
                .strokeWidth(this.strokeWidth)
                .strokeDashArray(this.strokeDashArray)
                .strokeLineCap(this.strokeLineCap)
                .strokeDashOffset(this.strokeDashOffset)
                .strokeLineJoin(this.strokeLineJoin)
                .strokeUniform(this.strokeUniform)
                .strokeMiterLimit(this.strokeMiterLimit)
                .scaleX(this.scaleX)
                .scaleY(this.scaleY)
                .angle(this.angle)
                .flipX(this.flipX)
                .flipY(this.flipY)
                .opacity(this.opacity)
                .shadow(this.shadow)
                .visible(this.visible)
                .backgroundColor(this.backgroundColor)
                .fillRule(this.fillRule)
                .paintFirst(this.paintFirst)
                .globalCompositeOperation(this.globalCompositeOperation)
                .skewX(this.skewX)
                .skewY(this.skewY)
                .typeId(this.typeId)
                .offMsg(this.offMsg)
                .notMsg(this.notMsg)
                .notImg(this.notImg)
                .offImg(this.offImg)
                .gatewayId(this.gatewayId)
                .machineType(this.machineType)
                .machineAddr(this.machineAddr)
                .didoType(this.didoType)
                .inOutNum(this.inOutNum)
                .erasable(this.erasable)
                .cropX(this.cropX)
                .cropY(this.cropY)
                .src(this.src)
                .crossOrigin(this.crossOrigin)
                .groupId(this.groupId)
                //.filters(this.filters)
                .rx(this.rx)
                .ry(this.ry)
                .splitByGrapheme(this.splitByGrapheme)
                .charSpacing(this.charSpacing)
                .direction(this.direction)
                .fontFamily(this.fontFamily)
                .fontSize(this.fontSize)
                .fontStyle(this.fontStyle)
                .fontWeight(this.fontWeight)
                .lineHeight(this.lineHeight)
                .linethrough(this.linethrough)
                .minWidth(this.minWidth)
                .overline(this.overline)
                .pathSide(this.pathSide)
                .pathStartOffset(this.pathStartOffset)
                .text(this.text)
                .textAlign(this.textAlign)
                .textBackgroundColor(this.textBackgroundColor)
                .underline(this.underline)
                .offFill(this.offFill)
                .notFill(this.notFill)
                .offBackColor(this.offBackColor)
                .notBackColor(this.notBackColor)
                .build();
    }
}
