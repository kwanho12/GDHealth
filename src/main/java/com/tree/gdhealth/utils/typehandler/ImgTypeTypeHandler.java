package com.tree.gdhealth.utils.typehandler;

import com.tree.gdhealth.dto.SportsEquipmentImg;
import com.tree.gdhealth.utils.enumtype.ImageType;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**<p>{@link SportsEquipmentImg}의 이미지 필드를 매핑하는 핸들러</p>
 * @author 정인호
 */
@MappedTypes(ImageType.class)
public class ImgTypeTypeHandler implements TypeHandler<ImageType> {
    @Override
    public void setParameter(PreparedStatement ps, int i, ImageType parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getContentType());
    }

    @Override
    public ImageType getResult(ResultSet rs, String columnName) throws SQLException {
        String contentType = rs.getString(columnName);
        return ImageType.fromText(contentType);
    }

    @Override
    public ImageType getResult(ResultSet rs, int columnIndex) throws SQLException {
        String contentType = rs.getString(columnIndex);
        return ImageType.fromText(contentType);
    }

    @Override
    public ImageType getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String contentType = cs.getString(columnIndex);
        return ImageType.fromText(contentType);
    }
}
