
name := "slickApp"

version   := 	"1.0"

scalaVersion := "2.11.4"


libraryDependencies ++= Seq(
                      "mysql"                %     "mysql-connector-java"     %      "5.1.21",
                      "org.scalatest"        %%    "scalatest"    	      %      "2.2.2"     %     "test",
		      "com.typesafe" % "config" % "1.2.1",
		      "ch.qos.logback"       %     "logback-classic"          %      "1.0.13",
    		      "com.typesafe.slick" %% "slick" % "2.1.0",
    		       "org.postgresql" % "postgresql" % "9.4-1200-jdbc41",
    		       "ch.qos.logback"             	%       "logback-classic"     	%       "1.0.13",
        	       "javax.mail" % "mail" % "1.4.5"	
    		       
                    )



parallelExecution in Test:=false
