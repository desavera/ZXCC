USE [ZX_CC_DEV]
GO

/****** Object:  Table [dbo].[CLIENTES]    Script Date: 05/12/2014 14:29:49 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[SCHED_WORK](
	[WORK_ID] [int] IDENTITY(1,1) NOT NULL,
	[WORK_NAME] [char](50) NOT NULL,
	[PROCESS_ID] [int]	NOT NULL,
	[SCHED_TIMESTAMP] [timestamp] NOT NULL,
	[START_TIMESTAMP] [datetime] NULL,
	[END_TIMESTAMP] [datetime] NULL,
	[RESTRICTION_ID] [int] NOT NULL,
	[PROCESS_STATE_ID] [int] NOT NULL,
	[DEPENDENCY_WORK_ID] [int] NULL,
	[WORK_ALERT_ID] [int] NOT NULL,
	[WORK_GROUP_ID] [int] NOT NULL,
	[DEFINED_PROCESS_ID] [int] NOT NULL,
	[DEFINED_WORK_ID] [int] NOT NULL,
	[COD_USUARIO] [int] NOT NULL,
	[PK_COLUMN] [int] NOT NULL,
	[WORK_STATE_ID] [int] NOT NULL,
	[ALERT_STATUS] [int] NOT NULL,
	CONSTRAINT [PK_WORK_ID] PRIMARY KEY CLUSTERED (
		[WORK_ID] ASC
	)
	WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE TABLE [dbo].[SCHED_PROCESS](
	[PROCESS_ID] [int] IDENTITY(1,1) NOT NULL,
	[PROCESS_NAME] [char](50) NOT NULL,
	[STATE_ID] [int] NOT NULL,
	CONSTRAINT [PK_PROCESS_ID] PRIMARY KEY CLUSTERED (
		[PROCESS_ID] ASC
	)
	WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE TABLE [dbo].[DEFINED_PROCESS_STATE](
	[PROCESS_STATE_ID] [int] IDENTITY(1,1) NOT NULL,
	[STATE_NAME] [char](50) NOT NULL,
	[NEXT_STATE_ID] [int] NULL,
	[FLUX_ID] [int] NOT NULL,
	[PROCESS_ID] [int] NOT NULL,
	CONSTRAINT [PK_PROCESS_STATE_ID] PRIMARY KEY CLUSTERED (
		[PROCESS_STATE_ID] ASC
	)
	WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE TABLE [dbo].[WORK_ALERT](
	[WORK_ALERT_ID] [int] IDENTITY(1,1) NOT NULL,
	[GROUP_ALERT_ID] [int] NOT NULL,
	CONSTRAINT [PK_WORK_ALERT_ID] PRIMARY KEY CLUSTERED (
		[WORK_ALERT_ID] ASC
	)
	WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE TABLE [dbo].[USER_GROUP_ALERT](
	[USER_GROUP_ID] [int] IDENTITY(1,1) NOT NULL,
	[GROUP_ALERT_ID] [int] NOT NULL,
	[COD_USUARIO] [int] NOT NULL,
	[EMAIL] [char](50) NOT NULL;
	CONSTRAINT [PK_USER_GROUP_ID] PRIMARY KEY CLUSTERED (
		[USER_GROUP_ID] ASC
	)
	WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE TABLE [dbo].[RESTRICTION_WORK](
	[RESTRICTION_ID] [int] IDENTITY(1,1) NOT NULL,
	[RESTRICTION_VALUE] [time](0) NOT NULL,
	CONSTRAINT [PK_RESTRICTION_ID] PRIMARY KEY CLUSTERED (
		[RESTRICTION_ID] ASC
	)
	WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE TABLE [dbo].[DEFINED_WORK](
	[WORK_ID] [int] IDENTITY(1,1) NOT NULL,
	[WORK_NAME] [char](50) NOT NULL,
	[PROCESS_ID] [int]	NOT NULL,
	[RESTRICTION_ID] [int] NOT NULL,
	[PROCESS_STATE_ID] [int] NOT NULL,
	[DEPENDENCY_WORK_ID] [int] NULL,
	[WORK_ALERT_ID] [int] NOT NULL,
	[WORK_GROUP_ID] [int] NOT NULL,
	CONSTRAINT [PK_WORK_ID] PRIMARY KEY CLUSTERED (
		[WORK_ID] ASC
	)
	WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE TABLE [dbo].[DEFINED_PROCESS](
	[PROCESS_ID] [int] IDENTITY(1,1) NOT NULL,
	[PROCESS_NAME] [char](50) NOT NULL,
	[STATE_ID] [int] NOT NULL,
	CONSTRAINT [PK_PROCESS_ID] PRIMARY KEY CLUSTERED (
		[PROCESS_ID] ASC
	)
	WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE TABLE [dbo].[WORK_GROUP](
	[WORK_GROUP_ID] [int] IDENTITY(1,1) NOT NULL,
	[WORK_GROUP_NAME] char(50) NOT NULL,
	CONSTRAINT [PK_WORK_GROUP_ID] PRIMARY KEY CLUSTERED (
		[WORK_GROUP_ID] ASC
	)
	WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE TABLE [dbo].[WORK_USER](
	[WORK_USER_ID] [int] IDENTITY(1,1) NOT NULL,
	[WORK_GROUP_ID] [int] NOT NULL,
	[COD_USUARIO] [int] NOT NULL,
	CONSTRAINT [PK_WORK_USER_ID] PRIMARY KEY CLUSTERED (
		[WORK_USER_ID] ASC
	)
	WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE TABLE [dbo].[WORK_SERVICE](
	[WORK_SERVICE_ID] [int] IDENTITY(1,1) NOT NULL,
	[SERVICE_NAME] [char](50) NOT NULL,
	[PROCESS_ID] [int] NOT NULL,
	[WORK_ID] [int] NOT NULL,
	CONSTRAINT [PK_WORK_SERVICE_ID] PRIMARY KEY CLUSTERED (
		[WORK_SERVICE_ID] ASC
	)
	WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE TABLE [dbo].[WORK_STATUS](
	[WORK_STATUS_ID] [int] IDENTITY(1,1) NOT NULL,
	[WORK_STATUS_NAME] [char](50) NOT NULL,
	CONSTRAINT [PK_WORK_STATUS_ID] PRIMARY KEY CLUSTERED (
		[WORK_STATUS_ID] ASC
	)
	WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE WORK_SERVICE
ADD CONSTRAINT FK_WORK_SERVICE_DEFINED_WORK_PROCESS_ID
FOREIGN KEY(PROCESS_ID)
REFERENCES DEFINED_PROCESS(PROCESS_ID);

ALTER TABLE WORK_SERVICE
ADD CONSTRAINT FK_WORK_SERVICE_DEFINED_WORK_WORK_ID
FOREIGN KEY(WORK_ID)
REFERENCES DEFINED_WORK(WORK_ID);

ALTER TABLE SCHED_WORK
ADD CONSTRAINT FK_SCHED_WORK_RESTRICTION_WORD_RESTRICTION_ID
FOREIGN KEY(RESTRICTION_ID)
REFERENCES RESTRICTION_WORK(RESTRICTION_ID);

ALTER TABLE USER_GROUP
ADD CONSTRAINT FK_USER_GROUP_USUARIO_COD_USUARIO
FOREIGN KEY(COD_USUARIO)
REFERENCES USUARIO(COD_USUARIO);

ALTER TABLE SCHED_WORK
ADD CONSTRAINT FK_SCHED_WORK_SCHED_PROCESS_PROCESS_ID
FOREIGN KEY(PROCESS_ID)
REFERENCES SCHED_PROCESS(PROCESS_ID);

ALTER TABLE SCHED_WORK
ADD CONSTRAINT FK_SCHED_WORK_WORK_ALERT_WORK_ALERT_ID
FOREIGN KEY(WORK_ALERT_ID)
REFERENCES WORK_ALERT(WORK_ALERT_ID);

ALTER TABLE DEFINED_WORK
ADD CONSTRAINT FK_DEFINED_WORK_RESTRICTION_WORD_RESTRICTION_ID
FOREIGN KEY(RESTRICTION_ID)
REFERENCES RESTRICTION_WORK(RESTRICTION_ID);

ALTER TABLE DEFINED_WORK
ADD CONSTRAINT FK_DEFINED_WORK_DEFINED_PROCESS_PROCESS_ID
FOREIGN KEY(PROCESS_ID)
REFERENCES DEFINED_PROCESS(PROCESS_ID);

ALTER TABLE DEFINED_WORK
ADD CONSTRAINT FK_DEFINED_WORK_WORK_ALERT_WORK_ALERT_ID
FOREIGN KEY(WORK_ALERT_ID)
REFERENCES WORK_ALERT(WORK_ALERT_ID);

ALTER TABLE DEFINED_PROCESS
ADD CONSTRAINT FK_DEFINED_PROCESS_DEFINED_PROCESS_STATE_STATE_ID
FOREIGN KEY(STATE_ID)
REFERENCES DEFINED_PROCESS_STATE(PROCESS_STATE_ID);

ALTER TABLE DEFINED_WORK
ADD CONSTRAINT FK_DEFINED_WORK_DEFINED_PROCESS_STATE_STATE_ID
FOREIGN KEY(PROCESS_STATE_ID)
REFERENCES DEFINED_PROCESS_STATE(PROCESS_STATE_ID);

ALTER TABLE SCHED_PROCESS
ADD CONSTRAINT FK_SCHED_PROCESS_DEFINED_PROCESS_STATE_STATE_ID
FOREIGN KEY(STATE_ID)
REFERENCES DEFINED_PROCESS_STATE(PROCESS_STATE_ID);

ALTER TABLE SCHED_WORK
ADD CONSTRAINT FK_SCHED_WORK_DEFINED_PROCESS_STATE_STATE_ID
FOREIGN KEY(PROCESS_STATE_ID)
REFERENCES DEFINED_PROCESS_STATE(PROCESS_STATE_ID);

ALTER TABLE DEFINED_WORK
ADD CONSTRAINT FK_DEFINED_WORK_WORK_GROUP_WORK_GROUP_ID
FOREIGN KEY(WORK_GROUP_ID)
REFERENCES WORK_GROUP(WORK_GROUP_ID);

ALTER TABLE SCHED_WORK
ADD CONSTRAINT FK_SCHED_WORK_WORK_GROUP_WORK_GROUP_ID
FOREIGN KEY(WORK_GROUP_ID)
REFERENCES WORK_GROUP(WORK_GROUP_ID);

ALTER TABLE WORK_USER
ADD CONSTRAINT FK_WORK_USER_WORK_GROUP_WORK_GROUP_ID
FOREIGN KEY(WORK_GROUP_ID)
REFERENCES WORK_GROUP(WORK_GROUP_ID);

ALTER TABLE WORK_USER
ADD CONSTRAINT FK_WORK_USER_USUARIO_COD_USUARIO
FOREIGN KEY(COD_USUARIO)
REFERENCES USUARIO(COD_USUARIO);

ALTER TABLE SCHED_WORK
ADD CONSTRAINT FK_SCHED_WORK_DEFINED_PROCESS_PROCESS_ID
FOREIGN KEY(DEFINED_PROCESS_ID)
REFERENCES DEFINED_PROCESS(PROCESS_ID);

ALTER TABLE DEFINED_PROCESS_STATE
ADD CONSTRAINT FK_DEFINED_PROCESS_STATE_DEFINED_WORK_PROCESS_ID
FOREIGN KEY(PROCESS_ID)
REFERENCES DEFINED_PROCESS(PROCESS_ID);

ALTER TABLE SCHED_WORK
ADD CONSTRAINT FK_SCHED_WORK_DEFINED_WORK_WORK_ID
FOREIGN KEY(DEFINED_WORK_ID)
REFERENCES DEFINED_WORK(WORK_ID);

ALTER TABLE SCHED_WORK
ADD CONSTRAINT FK_SCHED_WORK_WORK_STATUS_WORK_STATUS_ID
FOREIGN KEY(WORK_STATE_ID)
REFERENCES WORK_STATUS(WORK_STATUS_ID);