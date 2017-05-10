package com.controller;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.diff.output.DiffOutputControl;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.integration.commandline.CommandLineUtils;
import liquibase.resource.CompositeResourceAccessor;
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.snapshot.InvalidExampleException;
import liquibase.util.StringUtils;

import org.liquibase.maven.plugins.MavenResourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class MainController {

	private static final String CHANGES_TO_MIGRATE = "tables,views,columns,indexes,foreignkeys,primarykeys,uniqueconstraints,data";
	private static final String TEMP_FILE_LOCATION = "C:/temp";
	private static final String DB_SOURCE_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_SOURCE_PASSWORD = "admin";
	private static final String DB_SOURCE_USERNAME = "root";
	private static final String DB_SOURCE_URL = "jdbc:mysql://localhost:3306/test";
	
	private static final String DB_DEST_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_DEST_PASSWORD = "admin";
	private static final String DB_DEST_USERNAME = "root";
	private static final String DB_DEST_URL = "jdbc:mysql://localhost:3306/test2";

	@RequestMapping(value = "/backup.htm", method = RequestMethod.GET)
	public String name() throws Exception {

		migrateDB();
		return "home";
	}

	public void migrateDB() throws DatabaseException, IOException,
			ParserConfigurationException, InvalidExampleException,
			LiquibaseException {
		Database database = CommandLineUtils.createDatabaseObject(getClass()
				.getClassLoader(), DB_SOURCE_URL, DB_SOURCE_USERNAME,
				DB_SOURCE_PASSWORD, DB_SOURCE_DRIVER, null, null, false, false,
				null, null, null, null, null, null, null);

		String tempChangeFileName = "liquibase-"+System.currentTimeMillis()+".xml";
		CommandLineUtils
				.doGenerateChangeLog(
						TEMP_FILE_LOCATION+"/"+tempChangeFileName,
						database,
						null,
						null,
						StringUtils
								.trimToNull(CHANGES_TO_MIGRATE),
						null, null, null, new DiffOutputControl(false, false,
								true));


		Database toDatabase = CommandLineUtils.createDatabaseObject(getClass()
				.getClassLoader(), DB_DEST_URL, DB_DEST_USERNAME,
				DB_DEST_PASSWORD, DB_DEST_DRIVER, null, null, false, false,
				null, null, null, null, null, null, null);
	
		Liquibase liquibase = new Liquibase(tempChangeFileName,
				new liquibase.resource.FileSystemResourceAccessor(
						TEMP_FILE_LOCATION), toDatabase);
		//clean destination database(tables) before migrate
		liquibase.dropAll();

		liquibase.update(null, new Contexts(1 == 1 ? null : ""),
				new LabelExpression(1 == 1 ? null : ""));
	}
}