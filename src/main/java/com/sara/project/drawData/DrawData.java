package com.sara.project.drawData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@Table(name="DRAW_DATA")
public class DrawData {

    @Id
    private Long id;

    @Column
    private String type;

    @Column
    private String version;
    @Column
    private String originX;
    @Column
    private String originY;

    @Column(columnDefinition="Decimal(12,3) default '0.000'")
    private BigDecimal leftSize;

    @Column(columnDefinition="Decimal(12,3) default '0.000'")
    private BigDecimal top;

    @Column(columnDefinition="Decimal(12,3) default '0.000'")
    private BigDecimal width;

    @Column(columnDefinition="Decimal(12,3) default '0.000'")
    private BigDecimal height;


    @Column
    private String fill;
    @Column
    private String stroke;

    @Column
    private Integer strokeWidth;

    @Column
    private String strokeDashArray;

    @Column
    private String strokeLineCap;

    @Column
    private Integer strokeDashOffset;

    @Column
    private String strokeLineJoin;

    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean strokeUniform;

    @Column
    private Integer strokeMiterLimit;

    @Column(columnDefinition="Decimal(12,3) default '0.000'")
    private BigDecimal scaleX;

    @Column(columnDefinition="Decimal(12,3) default '0.000'")
    private BigDecimal scaleY;

    @Column
    private Integer angle;

    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean flipX;


    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean flipY;


    @Column
    private Integer opacity;

    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean visible;

    @Column
    private String backgroundColor;

    @Column
    private String fillRule;

    @Column
    private String paintFirst;

    @Column
    private String globalCompositeOperation;


    @Column
    private Integer skewX;

    @Column
    private Integer skewY;

    @Column
    private Long typeId;

    @Column
    private String offMsg;

    @Column
    private String notMsg;

    @Lob
    @Column
    private String notImg;

    @Lob
    @Column
    private String offImg;

    @Column
    private String gatewayId;

    @Column
    private String machineType;

    @Column
    private String machineAddr;

    @Column
    private String didoType;

    @Column
    private String inOutNum;

    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean erasable;

    @Column
    private Integer cropX;

    @Column
    private Integer cropY;

    @Lob
    @Column
    private String src;

    @Column
    private Integer crossOrigin;

    @Column
    private String filters;

    @Column
    private Long groupId;

    @Column
    private String groupType;
/* 새로 추가*/
    @Column
    private Integer rx;

    @Column
    private Integer ry;

    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean splitByGrapheme;

    @Column
    private Integer charSpacing;

    @Column
    private String direction;

    @Column
    private String fontFamily;

    @Column(columnDefinition="Decimal(12,3) default '0.000'")
    private BigDecimal fontSize;

    @Column
    private String fontStyle;

    @Column
    private String fontWeight;

    @Column(columnDefinition="Decimal(12,3) default '0.000'")
    private BigDecimal lineHeight;

    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean linethrough;

    @Column(columnDefinition="Decimal(12,3) default '0.000'")
    private BigDecimal minWidth;

    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean overline;



    @Column
    private String pathSide;

    @Column
    private Integer pathStartOffset;


    @Column
    private String text;
    @Column
    private String textAlign;
    @Column
    private String textBackgroundColor;


    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean underline;

    // path: null 추후 추가 예정
    //styles: {} 추후 추가 예정
    // fillter: 추후 추가 예정

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(targetEntity = Shadow.class)
    @JoinColumn(name = "SHADOW_ID")
    private Shadow shadow;

    /*20211121 추가 내역*/

    @Column
    private String offFill;

    @Column
    private String notFill;


    @Column
    private String offBackColor;


    @Column
    private String notBackColor;

}

