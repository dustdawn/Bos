OA:办公自动化系统
CRM:客户关系管理系统
ERP: 企业资源计划   综合的企业解决方案（平台）

bos-parent
	|
	-----bos-utils
	|
	-----bos-web
	|
	-----bos-service
	|
	-----bos-dao
	|
	-----bos-entity
	
	延迟加载
	当Hibernate从数据库中加载某个对象时，不加载关联的对象(不执行sql语句)，而只是生成了代理对象，获取使
	用session中的load的方法(在没有改变lazy属性为false的情况下)获取到的也是代理对象，所以在上面这几种场景下就是延迟加载。
	
	立即加载
 	 当Hibernate从数据库中加载某个对象时，加载关联的对象，生成的实际对象，获取使用session中的get的方法获取到的是实际对象。
	
	hibernate延迟加载问题，延迟session关闭：展示数据到向jsp页面即从数据库加载某个对象时，要加载关联的对象，而
	Hibernate延迟加载则是生成代理对象，不加载关联对象，需要获取关联对象时，才调用session中的load方法，此时可能
	由于session已经关闭 ，而导致加载关联对象失败。通过过滤器发送请求和返回响应时都要经过过滤器，延迟session的关闭时间
	从而保证延迟加载操作的顺利进行 。
	

	
	第一步：配置web.xml文件
		
		<!-- 配置过滤器，解决hibernate延迟加载问题，延迟session关闭 -->
			<!-- openSessionInView过滤器的主要功能是延迟Session的关闭时间，从而保证延迟加载操作的顺利进行 
				Hibernate 允许对关联对象、属性进行延迟加载，但是必须保证延迟加载的操作限于同一个 Hibernate Session
				范围之内进行。如果 Service 层返回一个启用了延迟加载功能的领域对象给 Web 层，当 Web 层访问到那些需要延迟
				加载的数据时，由于加载领域对象的 Hibernate Session 已经关闭，这些导致延迟加载数据的访问异常
			-->
				  <filter>
				  	<filter-name>openSessionInView</filter-name>
				  	<filter-class>org.springframework.orm.hibernate5.support.OpenSessionInViewFilter</filter-class>
				  </filter>
				  <filter-mapping>
				  	<filter-name>openSessionInView</filter-name>
				  	<url-pattern>/*</url-pattern>
				  </filter-mapping>
		<!-- 通过上下文参数指定spring配置文件位置 -->
			  <context-param>
			  	<param-name>contextConfigLocation</param-name>
			  	<param-value>classpath:applicationContext.xml</param-value>
			  </context-param>
  
  		<!-- 配置spring框架的监听器 -->
  			<!-- ContextLoaderListener是由Spring提供的一个监听器类，它在创建时会自动查找名为
  				contextConfigLocation的初始化参数,并使用该参数所指定的配置文件，此处表示application.xml文件
  			 -->
				  <listener>
				  	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
				  </listener>
  		<!--  -->
  		<!-- 配置struts2的过滤器 -->
				  <filter>
				  	<filter-name>struts2</filter-name>
				  	<filter-class>org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter</filter-class>
				  </filter>
				  <filter-mapping>
				  	<filter-name>struts2</filter-name>
				  	<url-pattern>/*</url-pattern>
			<!-- 拦截一切请求，服务器内部的转发也拦截 -->
				  	<dispatcher>REQUEST</dispatcher>
				  	<dispatcher>FORWARD</dispatcher>
				  </filter-mapping>
				  
				  
				  
				  
	第二步：配置struts.xml文件
			<struts>
					<!-- 控制台信息详细度 -->
					<constant name="struts.devMode" value="false" />
					<!-- struts2中的action对象由spring创建，整合包中已经整合
						<constant name="struts.objectFactory" value="spring"/>
					 -->
					<package name="basicstruts2" extends="struts-default">
						<!-- 需要进行权限控制的页面访问 -->
						<!-- 执行的是默认action  ActionSupport中的execute()方法 
							不能直接访问WEB-INF下的文件，通过调用ActionSupport的execute转发跳转
						-->
						
					<interceptors>
			<!-- 自定义拦截器 -->
							<interceptor name="loginInterceptor" class="com.dustdawn.bos.web.interceptor.LoginInterceptor">
								<!-- 指定不需要拦截的方法 -->
								<param name="excludeMethods">login</param>
							</interceptor>
								<!-- 定义拦截器栈 -->
							<interceptor-stack name="myStack">
								<interceptor-ref name="loginInterceptor"></interceptor-ref>
								<interceptor-ref name="defaultStack"></interceptor-ref>
							</interceptor-stack>	
						</interceptors>
			<!-- 配置默认拦截器栈 -->
						<default-interceptor-ref name="myStack"></default-interceptor-ref>
			<!-- 全局结果集 -->
						<global-results>
							<result name="login">/login.jsp</result>
						</global-results>
		
		
		
						<action name="page_*_*">
							<result type="dispatcher">/WEB-INF/pages/{1}/{2}.jsp</result>
						</action>
					</package>
			</struts>
	
	第三步：配置log4j.properties日志文件
		###ConsoleAppender:控制台日志记录器
		###FileAppender:文件日志记录器
		###fatal致命信息 error普通错误 warn警告信息 info普通信息 debug调试信息 trace堆栈信息
				### direct log messages to stdout ###
				log4j.appender.stdout=org.apache.log4j.ConsoleAppender
				log4j.appender.stdout.Target=System.err
				log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
				log4j.appender.stdout.layout.ConversionPattern=%d{ABSOLUTE} %5p %c{1}:%L - %m%n
				
				### direct messages to file mylog.log ###
				log4j.appender.file=org.apache.log4j.FileAppender
				log4j.appender.file.File=d:\\mylog.log
				log4j.appender.file.layout=org.apache.log4j.PatternLayout
				log4j.appender.file.layout.ConversionPattern=%d{ABSOLUTE} %5p %c{1}:%L - %m%n
				
				### set log levels - for more verbose logging change 'info' to 'debug' ###
				### fatal error warn info debug trace
				log4j.rootLogger=debug, file
		
	第四步：配置applicationContext.xml文件
				<!-- 加载属性文件 -->
					<context:property-placeholder location="classpath:db.properties"/>
				
				<!-- 配置数据源 -->
					<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
						<property name="driverClass" value="${jdbc.driverClass}"/>
						<property name="jdbcUrl" value="${jdbc.jdbcUrl}"/>
						<property name="user" value="${jdbc.user}"/>
						<property name="password" value="${jdbc.password}"/>
					</bean>
				
				<!-- 配置LocalSessionFactoryBean，spring提供的用于整合hibernate的工厂bean -->
					<bean id="sessionFactory" class="org.springframework.orm.hibernate5.LocalSessionFactoryBean">
						<property name="dataSource" ref="dataSource"/>
				<!-- 注入hibernate相关的属性配置 -->
						<property name="hibernateProperties">
							<props>
								<prop key="hibernate.dialect">org.hibernate.dialect.MySQL5Dialect</prop>
								<prop key="hibernate.hbm2ddl.auto">update</prop>
								<prop key="hibernate.show_sql">true</prop>
								<prop key="hibernate.format_sql">true</prop>
							</props>
						</property>
				<!-- 注入hibernate的映射文件 -->
						<property name="mappingLocations">
							<list>
								<value>classpath:com/dustdawn/bos/entity/*.xml</value>
							</list>
						</property>
					</bean>
					
				<!-- 配置事务管理器 -->
						<bean id="transactionManager" class="org.springframework.orm.hibernate5.HibernateTransactionManager">
							<property name="sessionFactory" ref="sessionFactory"/>
						</bean>
				<!-- 组件扫描
					扫描类中所含注解,再控制反转创建类
				 -->
						<context:component-scan base-package="com.dustdawn.bos"/>
						
				<!-- 支持spring注解 -->
						<context:annotation-config/>		
				<!-- 事务注解 -->
						<tx:annotation-driven/>		
		
	
	第五步：创建一个项目的目录结构
	第六步：将项目使用的资源文件复制到项目中
	
	
	默认情况下，Hibernate所有的关联查询都是延迟加载(懒加载)，执行的是id的查询，查询结果返回的是代理对象，
		引用代理对象时才执行sql，造成关联查询死循环
	使用立即加载，查询结果返回的是查询好后封装的真正对象，所以引用是不会造成关联查询死循环
