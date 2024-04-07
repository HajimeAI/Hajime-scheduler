package ai.hajimebot;


import com.alibaba.druid.pool.DruidDataSource;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.ColumnConfig;
import com.mybatisflex.codegen.config.GlobalConfig;

/**
 * {@code author} xc.deng
 * {@code create} 2024/03/06 18:41
 */
public class Codegen {

    public static void main(String[] args) {
        //Configure the data source
        DruidDataSource dataSource = new DruidDataSource();

        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/hajime_test?useSSL=false&characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("Hajime888");

        //Create configuration content, either style.
        GlobalConfig globalConfig = createGlobalConfigUseStyle1();
        //GlobalConfig globalConfig = createGlobalConfigUseStyle2();

        Generator generator = new Generator(dataSource, globalConfig);

        //Generate code
        generator.generate();
    }

    public static GlobalConfig createGlobalConfigUseStyle1() {
        //Create configuration content
        GlobalConfig globalConfig = new GlobalConfig();

        //Set up annotations
        globalConfig.getJavadocConfig()
                .setAuthor("xc.deng")
                .setSince("1.0.1");

        //Set up the root package
        globalConfig.setBasePackage("ai.hajimebot");
        globalConfig.setSourceDir(System.getProperty("user.dir") + "/src/main/java");
        globalConfig.setMapperXmlPath(System.getProperty("user.dir") + "/src/main/resources/mapper");

        //Set table prefixes and which tables are generated
//        globalConfig.setTablePrefix("host_");
//        globalConfig.setGenerateTable("node","node_send","indexed","user");
        globalConfig.setGenerateTable("event");

        //Set up the spawn entity and enable Lombok
        globalConfig.enableEntity()
                .setWithLombok(true)
                .setWithSwagger(true)
                .setClassSuffix("Vo");

        //mapper
        globalConfig.enableMapper();

        //Service
        globalConfig.enableService();

        //ServiceImpl
        globalConfig.enableServiceImpl();

        //Controller
        globalConfig.enableController();

        globalConfig.enableMapperXml().setFileSuffix("Mapper");

//        globalConfig.enableTableDef();

        //You can configure a column individually
//        ColumnConfig columnConfig = new ColumnConfig();
//        columnConfig.setColumnName("tenant_id");
//        columnConfig.setLarge(true);
//        columnConfig.setVersion(true);
//        globalConfig.setColumnConfig("tb_account", columnConfig);

        return globalConfig;
    }

    public static GlobalConfig createGlobalConfigUseStyle2() {
        //Create configuration content
        GlobalConfig globalConfig = new GlobalConfig();

        //Set up the root package
        globalConfig.getPackageConfig()
                .setBasePackage("com.test");

        //Set the table prefix and which tables are generated, and generate all tables if setGenerateTable is not configured
        globalConfig.getStrategyConfig()
                .setTablePrefix("tb_")
                .setGenerateTable("tb_account", "tb_account_session");

        //Set up the spawn entity and enable Lombok
        globalConfig.enableEntity()
                .setWithLombok(true);

        //mapper
        globalConfig.enableMapper();

        //You can configure a column individually
        ColumnConfig columnConfig = new ColumnConfig();
        columnConfig.setColumnName("tenant_id");
        columnConfig.setLarge(true);
        columnConfig.setVersion(true);
        globalConfig.getStrategyConfig()
                .setColumnConfig("tb_account", columnConfig);

        return globalConfig;
    }
}
