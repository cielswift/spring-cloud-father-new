//package com.ciel.springcloudfathernewsso.reamember;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
//import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
//
//import javax.sql.DataSource;
//
///**
// * 自动建表，第一次启动时需要，第二次启动时注释掉
// * jdbcTokenRepositoryImpl.setCreateTableOnStartup(true);
// *
// * 在第二次登陆时,
// * 需要将RememberMeConfig类中的getPersistentTokenRepository方法中的jdbcTokenRepositoryImpl.setCreateTableOnStartup(true);
// * 这段代码注释掉, 否则会出现如下问题
// *aused by: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Table 'persistent_logins' already exists
// *
// *
// * 每次修改loadUserByUsername方法的返回值(用户权限)return new User(username,password,AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_abc,/insert,/delete"));
// * RememberMe功能都会失效,需要重新设置 ,
// * 不过不需要重新开启上面注意中的那段代码,
// * 只需要清空生成的persistent_logins数据表的数据库再重启项目即可
// *
// */
//@Configuration
//public class RememberMeConfig {
//
//	@Bean
//	public PersistentTokenRepository getPersistentTokenRepository(DataSource dataSource) {
//		JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
//		jdbcTokenRepositoryImpl.setDataSource(dataSource);
//		// 自动建表，第一次启动时需要，第二次启动时注释掉
//		//jdbcTokenRepositoryImpl.setCreateTableOnStartup(true);
//		return jdbcTokenRepositoryImpl;
//	}
//}
