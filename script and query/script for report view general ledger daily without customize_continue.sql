-- Update Asset Register Script_PartII
-- Mar 18, 2017 1:55:41 PM ICT
INSERT INTO AD_Process (AD_Process_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,IsReport,Value,IsDirectPrint,AccessLevel,EntityType,Statistic_Count,Statistic_Seconds,IsBetaFunctionality,IsServerProcess,ShowHelp,CopyFromProcess,AD_Process_UU) VALUES (1100246,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:55:40','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:55:40','YYYY-MM-DD HH24:MI:SS'),100,'Create General Ledger Daily View','Y','Create General Ledger Daily View','N','3','U',0,0,'N','N','Y','N','321cef43-816e-4dd0-b7e3-60d4f4bc0d69')
;

-- Mar 18, 2017 1:56:45 PM ICT
INSERT INTO AD_Table (AD_Table_ID,Name,TableName,LoadSeq,AccessLevel,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsSecurityEnabled,IsDeleteable,IsHighVolume,IsView,EntityType,ImportTable,IsChangeLog,ReplicationType,CopyColumnsFromTable,IsCentrallyMaintained,AD_Table_UU,Processing,DatabaseViewDrop,CopyComponentsFromView) VALUES (1100273,'t_tcsgeneralledgerdailyview','t_tcsgeneralledgerdailyview',0,'3',0,0,'Y',TO_TIMESTAMP('2017-03-18 13:56:45','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:56:45','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','N','N','U','N','N','L','N','Y','bb3d05c5-4e6d-40d5-8bfc-296254ca756f','N','N','N')
;

-- Mar 18, 2017 1:56:45 PM ICT
INSERT INTO AD_Sequence (Name,CurrentNext,IsAudited,StartNewYear,Description,IsActive,IsTableID,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,AD_Sequence_ID,IsAutoSequence,StartNo,IncrementNo,CurrentNextSys,AD_Sequence_UU) VALUES ('t_tcsgeneralledgerdailyview',1000000,'N','N','Table t_tcsgeneralledgerdailyview','Y','Y',0,0,TO_TIMESTAMP('2017-03-18 13:56:45','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:56:45','YYYY-MM-DD HH24:MI:SS'),100,1101679,'Y',1000000,1,200000,'2fb14a0f-0f12-49da-8867-b8301ef49dfa')
;

-- Mar 18, 2017 1:57:17 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,AD_Val_Rule_ID,ColumnName,DefaultValue,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107886,0.0,'Client','Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.',1100273,129,'AD_Client_ID','@#AD_Client_ID@',10,'N','N','Y','N','N','N',19,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:57:17','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:57:17','YYYY-MM-DD HH24:MI:SS'),100,102,'N','N','U','N','02b31a53-241b-46c3-9b1f-c9022f9c6158','N','D')
;

-- Mar 18, 2017 1:57:18 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,AD_Val_Rule_ID,ColumnName,DefaultValue,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107887,0.0,'Organization','Organizational entity within client','An organization is a unit of your client or legal entity - examples are store, department. You can share data between organizations.',1100273,104,'AD_Org_ID','@#AD_Org_ID@',10,'N','N','Y','N','N','N',19,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:57:17','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:57:17','YYYY-MM-DD HH24:MI:SS'),100,113,'N','N','U','N','f7929dce-b55c-4f57-912b-650ccfc86811','N','D')
;

-- Mar 18, 2017 1:57:18 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107888,0.0,'org_name',1100273,'org_name',60,'N','N','Y','N','N','N',10,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:57:18','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:57:18','YYYY-MM-DD HH24:MI:SS'),100,300017,'N','N','U','N','948cda31-e148-4e1b-80f2-3519c8251e04','N','N')
;

-- Mar 18, 2017 1:57:18 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107889,0.0,'Accounting Schema','Rules for accounting','An Accounting Schema defines the rules used in accounting such as costing method, currency and calendar',1100273,'C_AcctSchema_ID',10,'N','N','N','N','N','N',19,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:57:18','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:57:18','YYYY-MM-DD HH24:MI:SS'),100,181,'N','N','U','N','8c3ae00c-0462-4d38-8abe-1bcfca1f45b8','N','C')
;

-- Mar 18, 2017 1:57:18 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107890,0.0,'acct_schema_name',1100273,'acct_schema_name',60,'N','N','N','N','N','N',10,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:57:18','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:57:18','YYYY-MM-DD HH24:MI:SS'),100,1101256,'Y','N','U','N','e20effdf-5038-4270-86b1-843dc266d0a9','N','N')
;

-- Mar 18, 2017 1:57:18 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Reference_Value_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107891,0.0,'Account','Account used','The (natural) account used',1100273,'Account_ID',10,'N','N','N','N','N','N',18,132,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:57:18','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:57:18','YYYY-MM-DD HH24:MI:SS'),100,148,'N','N','U','N','441f3158-13d3-477e-ac9b-5ee40089d02c','N')
;

-- Mar 18, 2017 1:57:18 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107892,0.0,'account_no',1100273,'account_no',40,'N','N','N','N','N','N',10,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:57:18','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:57:18','YYYY-MM-DD HH24:MI:SS'),100,1101257,'Y','N','U','N','1b8aeafb-f564-4180-990a-148284658861','N','N')
;

-- Mar 18, 2017 1:57:19 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107893,0.0,'Account Name',1100273,'Account_Name',120,'N','N','N','N','N','N',14,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:57:19','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:57:19','YYYY-MM-DD HH24:MI:SS'),100,202278,'N','Y','U','N','d8c2675d-0ba9-424d-9064-8d2bf0408097','N','N')
;

-- Mar 18, 2017 1:57:19 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107894,0.0,'Account Date','Accounting Date','The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.',1100273,'DateAcct',29,'N','N','N','N','N','N',15,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:57:19','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:57:19','YYYY-MM-DD HH24:MI:SS'),100,263,'N','N','U','N','9c86fd1f-afed-4ec9-bc59-b861b9b5cccc','N','N')
;

-- Mar 18, 2017 1:57:19 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107895,0.0,'Period','Period of the Calendar','The Period indicates an exclusive range of dates for a calendar.',1100273,'C_Period_ID',10,'N','N','N','N','N','N',19,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:57:19','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:57:19','YYYY-MM-DD HH24:MI:SS'),100,206,'N','N','U','N','34f3043b-c522-4bdb-b3b0-2786e510054a','N','N')
;

-- Mar 18, 2017 1:57:19 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107896,0.0,'period_name',1100273,'period_name',60,'N','N','N','N','N','N',10,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:57:19','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:57:19','YYYY-MM-DD HH24:MI:SS'),100,1101258,'Y','N','U','N','d8d686c9-fb0e-4ab0-a530-b649c499a891','N','N')
;

-- Mar 18, 2017 1:57:19 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107897,0.0,'Accounted Debit','Accounted Debit Amount','The Account Debit Amount indicates the transaction amount converted to this organization''s accounting currency',1100273,'AmtAcctDr',131089,'N','N','N','N','N','N',12,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:57:19','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:57:19','YYYY-MM-DD HH24:MI:SS'),100,162,'N','N','U','N','0a6b64b8-255f-40ef-b525-a38d558299df','N')
;

-- Mar 18, 2017 1:57:20 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107898,0.0,'Accounted Credit','Accounted Credit Amount','The Account Credit Amount indicates the transaction amount converted to this organization''s accounting currency',1100273,'AmtAcctCr',131089,'N','N','N','N','N','N',12,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:57:20','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:57:20','YYYY-MM-DD HH24:MI:SS'),100,161,'N','N','U','N','594ac663-3a3a-4eb4-b36f-f3f57fcf7256','N')
;

-- Mar 18, 2017 1:57:20 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107899,0.0,'Source Credit','Source Credit Amount','The Source Credit Amount indicates the credit amount for this line in the source currency.',1100273,'AmtSourceCr',131089,'N','N','N','N','N','N',12,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:57:20','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:57:20','YYYY-MM-DD HH24:MI:SS'),100,164,'N','N','U','N','14e011c3-9767-4e86-97ba-156cf6716e82','N')
;

-- Mar 18, 2017 1:57:20 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107900,0.0,'Source Debit','Source Debit Amount','The Source Debit Amount indicates the credit amount for this line in the source currency.',1100273,'AmtSourceDr',131089,'N','N','N','N','N','N',12,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:57:20','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:57:20','YYYY-MM-DD HH24:MI:SS'),100,165,'N','N','U','N','4446e425-5157-49f0-9105-4e957d152677','N')
;

-- Mar 18, 2017 1:57:20 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107901,0.0,'Accounted Balance','Accounted Balance Amount','The Account Balance Amount indicates the transaction amount converted to this organization''s accounting currency',1100273,'AmtAcctBalance',131089,'N','N','N','N','N','N',12,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:57:20','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:57:20','YYYY-MM-DD HH24:MI:SS'),100,2649,'N','N','U','N','9e74b00b-ad3c-4c66-877d-977b2654673a','N','N')
;

-- Mar 18, 2017 1:57:20 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107902,0.0,'Process Instance','Instance of the process',1100273,'AD_PInstance_ID',10,'N','N','N','N','N','N',19,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:57:20','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:57:20','YYYY-MM-DD HH24:MI:SS'),100,114,'N','N','U','N','985b89ab-0993-4d35-b9a5-289655bb9843','N','C')
;

-- Mar 18, 2017 1:57:21 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107903,0.0,'Sequence',1100273,'Sequence',10,'N','N','N','N','N','N',22,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:57:20','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:57:20','YYYY-MM-DD HH24:MI:SS'),100,52016,'Y','N','U','N','389c78e1-4ff2-465d-90c3-d04b4ece7aaf','N','N')
;

-- Mar 18, 2017 1:57:34 PM ICT
INSERT INTO AD_ReportView (AD_ReportView_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,AD_Table_ID,EntityType,AD_ReportView_UU) VALUES (1100070,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:57:34','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:57:34','YYYY-MM-DD HH24:MI:SS'),100,'General Ledger  Daily View',1100273,'U','9f3661ed-6e24-4174-880a-fb46f00239d5')
;

-- Mar 18, 2017 1:57:44 PM ICT
UPDATE AD_Process SET AD_ReportView_ID=1100070,Updated=TO_TIMESTAMP('2017-03-18 13:57:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=1100246
;

-- Mar 18, 2017 2:03:37 PM ICT
INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,Description,Help,AD_Process_ID,SeqNo,AD_Reference_ID,IsRange,FieldLength,IsMandatory,ColumnName,IsCentrallyMaintained,EntityType,AD_Element_ID,AD_Process_Para_UU,IsEncrypted) VALUES (1100515,0,0,'Y',TO_TIMESTAMP('2017-03-18 14:03:36','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 14:03:36','YYYY-MM-DD HH24:MI:SS'),100,'Account Date','Accounting Date','The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.',1100246,10,15,'Y',0,'Y','DateAcct','Y','U',263,'746a22f3-1552-4137-b621-9f09c195b0a4','N')
;

-- Mar 18, 2017 2:03:47 PM ICT
UPDATE AD_Process_Para SET Name='Date',Updated=TO_TIMESTAMP('2017-03-18 14:03:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=1100515
;

-- Mar 18, 2017 2:04:06 PM ICT
INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,Description,Help,AD_Process_ID,SeqNo,AD_Reference_ID,AD_Reference_Value_ID,IsRange,FieldLength,IsMandatory,ColumnName,IsCentrallyMaintained,EntityType,AD_Element_ID,AD_Process_Para_UU,IsEncrypted) VALUES (1100516,0,0,'Y',TO_TIMESTAMP('2017-03-18 14:04:06','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 14:04:06','YYYY-MM-DD HH24:MI:SS'),100,'Account','Account used','The (natural) account used',1100246,20,30,331,'Y',0,'Y','Account_ID','Y','U',148,'8c902044-adbc-42eb-bc58-6d14b7db44e3','N')
;

-- Mar 18, 2017 2:04:37 PM ICT
UPDATE AD_Process SET Classname='org.toba.habco.process.TCSGeneralLedgerDailyView',Updated=TO_TIMESTAMP('2017-03-18 14:04:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=1100246
;

-- Mar 18, 2017 2:04:47 PM ICT
INSERT INTO AD_Menu (AD_Menu_ID,Name,Description,"action",AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsSummary,AD_Process_ID,IsSOTrx,IsReadOnly,EntityType,IsCentrallyMaintained,AD_Menu_UU) VALUES (1100198,'Create General Ledger Daily View','Create General Ledger Daily View','P',0,0,'Y',TO_TIMESTAMP('2017-03-18 14:04:47','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 14:04:47','YYYY-MM-DD HH24:MI:SS'),100,'N',1100246,'N','N','U','Y','aedd2f52-f15c-4df9-be88-950cf502b5d1')
;

-- Mar 18, 2017 2:04:47 PM ICT
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo, AD_TreeNodeMM_UU) SELECT t.AD_Client_ID, 0, 'Y', statement_timestamp(), 100, statement_timestamp(), 100,t.AD_Tree_ID, 1100198, 0, 999, Generate_UUID() FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.TreeType='MM' AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=1100198)
;

-- Mar 18, 2017 2:06:29 PM ICT
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID=1100515
;

-- Mar 18, 2017 2:06:29 PM ICT
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=1100515
;

-- Mar 18, 2017 2:06:48 PM ICT
INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,Description,Help,AD_Process_ID,SeqNo,AD_Reference_ID,IsRange,FieldLength,IsMandatory,ColumnName,IsCentrallyMaintained,EntityType,AD_Element_ID,AD_Process_Para_UU,IsEncrypted) VALUES (1100517,0,0,'Y',TO_TIMESTAMP('2017-03-18 14:06:48','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 14:06:48','YYYY-MM-DD HH24:MI:SS'),100,'Account Date','Accounting Date','The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.',1100246,30,15,'N',0,'N','DateAcct','Y','U',263,'e5188329-b9c3-4243-a4c2-0d808ce57ce9','N')
;

