package cool.scx.jdbc.sql_server;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import cool.scx.jdbc.JDBCType;
import cool.scx.jdbc.dialect.Dialect;
import cool.scx.jdbc.type_handler.TypeHandler;
import cool.scx.jdbc.type_handler.TypeHandlerSelector;

import javax.sql.DataSource;
import java.lang.reflect.Type;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * todo 待完成
 *
 * @author scx567888
 * @version 0.0.1
 */
public class SQLServerDialect implements Dialect {

    private static final SQLServerDriver DRIVER = initDRIVER();
    
    private final TypeHandlerSelector typeHandlerSelector = new TypeHandlerSelector();

    private static SQLServerDriver initDRIVER() {
        return new SQLServerDriver();
    }
    

    @Override
    public boolean canHandle(String url) {
        try {
            return DRIVER.acceptsURL(url);
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean canHandle(DataSource dataSource) {
        try {
            return dataSource instanceof SQLServerDataSource || dataSource.isWrapperFor(SQLServerDataSource.class);
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean canHandle(Driver driver) {
        return driver instanceof SQLServerDriver;
    }

    @Override
    public String getFinalSQL(Statement statement) {
        return "";
    }

    @Override
    public DataSource createDataSource(String url, String username, String password, String[] parameters) {
        var sqlServerDataSource = new SQLServerDataSource();
        sqlServerDataSource.setURL(url);
        sqlServerDataSource.setUser(username);
        sqlServerDataSource.setPassword(password);
        return sqlServerDataSource;
    }

    @Override
    public <T> TypeHandler<T> findTypeHandler(Type type) {
        return typeHandlerSelector.findTypeHandler(type);
    }

    @Override
    public JDBCType dialectDataTypeToJDBCType(String dialectDataType) {
        return null;
    }

    @Override
    public String jdbcTypeToDialectDataType(JDBCType jdbcType) {
        return "";
    }

    @Override
    public String getDataTypeNameByJDBCType(JDBCType dataType) {
        return dataType.name();
    }

    @Override
    public String quoteIdentifier(String identifier) {
        return "[" + identifier + "]";
    }
    
}
