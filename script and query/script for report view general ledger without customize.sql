-- Update Asset Register Script_PartII
-- Mar 18, 2017 1:13:04 PM ICT
INSERT INTO AD_Process (AD_Process_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,IsReport,Value,IsDirectPrint,AccessLevel,EntityType,Statistic_Count,Statistic_Seconds,IsBetaFunctionality,IsServerProcess,ShowHelp,CopyFromProcess,AD_Process_UU) VALUES (1100245,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:13:04','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:13:04','YYYY-MM-DD HH24:MI:SS'),100,'Create General Ledger View','N','Create General Ledger View','N','3','U',0,0,'N','N','Y','N','4f03d876-56c8-4292-b319-e905363ce020')
;

-- Mar 18, 2017 1:13:33 PM ICT
UPDATE AD_Process SET Classname='org.toba.habco.process.TCSGeneralLedgerView',Updated=TO_TIMESTAMP('2017-03-18 13:13:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=1100245
;

-- Mar 18, 2017 1:15:05 PM ICT
INSERT INTO AD_Table (AD_Table_ID,Name,TableName,LoadSeq,AccessLevel,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsSecurityEnabled,IsDeleteable,IsHighVolume,IsView,EntityType,ImportTable,IsChangeLog,ReplicationType,CopyColumnsFromTable,IsCentrallyMaintained,AD_Table_UU,Processing,DatabaseViewDrop,CopyComponentsFromView) VALUES (1100271,'t_tcsgeneralledgerview','t_tcsgeneralledgerview',0,'4',0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:05','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','N','N','U','N','N','L','N','Y','ba10174c-df50-4bb5-9077-37fa65bee183','N','N','N')
;

-- Mar 18, 2017 1:15:06 PM ICT
INSERT INTO AD_Sequence (Name,CurrentNext,IsAudited,StartNewYear,Description,IsActive,IsTableID,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,AD_Sequence_ID,IsAutoSequence,StartNo,IncrementNo,CurrentNextSys,AD_Sequence_UU) VALUES ('t_tcsgeneralledgerview',1000000,'N','N','Table t_tcsgeneralledgerview','Y','Y',0,0,TO_TIMESTAMP('2017-03-18 13:15:05','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:05','YYYY-MM-DD HH24:MI:SS'),100,1101678,'Y',1000000,1,200000,'a4df495e-fb19-46de-8b82-11fab6b977e8')
;

-- Mar 18, 2017 1:15:09 PM ICT
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2017-03-18 13:15:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=1100271
;

-- Mar 18, 2017 1:15:15 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,AD_Val_Rule_ID,ColumnName,DefaultValue,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107844,0.0,'Client','Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.',1100271,129,'AD_Client_ID','@#AD_Client_ID@',10,'N','N','Y','N','N','N',19,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:14','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:14','YYYY-MM-DD HH24:MI:SS'),100,102,'N','N','U','N','50a4bb25-4d20-4b8f-b236-4c13ea9fe9c6','N','D')
;

-- Mar 18, 2017 1:15:15 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,AD_Val_Rule_ID,ColumnName,DefaultValue,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107845,0.0,'Organization','Organizational entity within client','An organization is a unit of your client or legal entity - examples are store, department. You can share data between organizations.',1100271,104,'AD_Org_ID','@#AD_Org_ID@',10,'N','N','Y','N','N','N',19,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:15','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:15','YYYY-MM-DD HH24:MI:SS'),100,113,'N','N','U','N','f570af2c-8d59-4052-b78d-2e5b8ac592d0','N','D')
;

-- Mar 18, 2017 1:15:15 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107846,0.0,'org_name',1100271,'org_name',60,'N','N','Y','N','N','N',10,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:15','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:15','YYYY-MM-DD HH24:MI:SS'),100,300017,'N','N','U','N','a361ae53-3312-4f43-a6bc-277c1ef79a9b','N','N')
;

-- Mar 18, 2017 1:15:15 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107847,0.0,'Accounting Schema','Rules for accounting','An Accounting Schema defines the rules used in accounting such as costing method, currency and calendar',1100271,'C_AcctSchema_ID',10,'N','N','N','N','N','N',19,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:15','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:15','YYYY-MM-DD HH24:MI:SS'),100,181,'N','N','U','N','2fc00cd8-5ac2-4b93-b80a-ef0714a4dc57','N','C')
;

-- Mar 18, 2017 1:15:15 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107848,0.0,'acct_schema_name',1100271,'acct_schema_name',60,'N','N','N','N','N','N',10,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:15','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:15','YYYY-MM-DD HH24:MI:SS'),100,1101256,'Y','N','U','N','7da8fab5-e20b-46b8-8215-fc76b464b304','N','N')
;

-- Mar 18, 2017 1:15:16 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Reference_Value_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107849,0.0,'Account','Account used','The (natural) account used',1100271,'Account_ID',10,'N','N','N','N','N','N',18,132,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:15','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:15','YYYY-MM-DD HH24:MI:SS'),100,148,'N','N','U','N','2008e74c-acad-482c-95e9-92e60cc0119c','N')
;

-- Mar 18, 2017 1:15:16 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107850,0.0,'account_no',1100271,'account_no',40,'N','N','N','N','N','N',10,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:16','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:16','YYYY-MM-DD HH24:MI:SS'),100,1101257,'Y','N','U','N','5555da6a-ced1-4b4c-aa06-b7bc73f51908','N','N')
;

-- Mar 18, 2017 1:15:16 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107851,0.0,'Account Name',1100271,'Account_Name',120,'N','N','N','N','N','N',14,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:16','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:16','YYYY-MM-DD HH24:MI:SS'),100,202278,'N','Y','U','N','6381da58-f2d7-4929-88a9-5ae9838c0c36','N','N')
;

-- Mar 18, 2017 1:15:16 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107852,0.0,'Account Date','Accounting Date','The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.',1100271,'DateAcct',29,'N','N','N','N','N','N',15,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:16','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:16','YYYY-MM-DD HH24:MI:SS'),100,263,'N','N','U','N','963a37b0-10db-4e34-87ad-8523c3e70bcd','N','N')
;

-- Mar 18, 2017 1:15:16 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107853,0.0,'Period','Period of the Calendar','The Period indicates an exclusive range of dates for a calendar.',1100271,'C_Period_ID',10,'N','N','N','N','N','N',19,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:16','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:16','YYYY-MM-DD HH24:MI:SS'),100,206,'N','N','U','N','e4872840-e9e2-4573-8183-118971c2a3aa','N','N')
;

-- Mar 18, 2017 1:15:17 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107854,0.0,'period_name',1100271,'period_name',60,'N','N','N','N','N','N',10,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:16','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:16','YYYY-MM-DD HH24:MI:SS'),100,1101258,'Y','N','U','N','9418568e-6d0d-4b0b-80e6-05b818f9e85c','N','N')
;

-- Mar 18, 2017 1:15:17 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Reference_Value_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107855,0.0,'PostingType','The type of posted amount for the transaction','The Posting Type indicates the type of amount (Actual, Budget, Reservation, Commitment, Statistical) the transaction.',1100271,'PostingType',1,'N','N','N','N','N','N',17,125,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:17','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:17','YYYY-MM-DD HH24:MI:SS'),100,514,'Y','N','U','N','86ff9ea1-473b-4213-aacb-7f7c0231d0cc','N')
;

-- Mar 18, 2017 1:15:17 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107856,0.0,'Accounted Debit','Accounted Debit Amount','The Account Debit Amount indicates the transaction amount converted to this organization''s accounting currency',1100271,'AmtAcctDr',131089,'N','N','N','N','N','N',12,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:17','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:17','YYYY-MM-DD HH24:MI:SS'),100,162,'N','N','U','N','b7ba6453-229b-4a81-9be4-877b4de9a87f','N')
;

-- Mar 18, 2017 1:15:17 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107857,0.0,'Accounted Credit','Accounted Credit Amount','The Account Credit Amount indicates the transaction amount converted to this organization''s accounting currency',1100271,'AmtAcctCr',131089,'N','N','N','N','N','N',12,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:17','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:17','YYYY-MM-DD HH24:MI:SS'),100,161,'N','N','U','N','b87e2a6e-30de-473c-953a-d4da84e98039','N')
;

-- Mar 18, 2017 1:15:17 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107858,0.0,'Source Credit','Source Credit Amount','The Source Credit Amount indicates the credit amount for this line in the source currency.',1100271,'AmtSourceCr',131089,'N','N','N','N','N','N',12,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:17','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:17','YYYY-MM-DD HH24:MI:SS'),100,164,'N','N','U','N','6726307f-ef8e-42d6-8597-b4d967e25e07','N')
;

-- Mar 18, 2017 1:15:18 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107859,0.0,'Source Debit','Source Debit Amount','The Source Debit Amount indicates the credit amount for this line in the source currency.',1100271,'AmtSourceDr',131089,'N','N','N','N','N','N',12,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:17','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:17','YYYY-MM-DD HH24:MI:SS'),100,165,'N','N','U','N','f72e6909-8d90-4309-9fac-b3f6c2536253','N')
;

-- Mar 18, 2017 1:15:18 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107860,0.0,'Accounted Balance','Accounted Balance Amount','The Account Balance Amount indicates the transaction amount converted to this organization''s accounting currency',1100271,'AmtAcctBalance',131089,'N','N','N','N','N','N',12,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:18','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:18','YYYY-MM-DD HH24:MI:SS'),100,2649,'N','N','U','N','8b1618f0-67ed-468c-96be-01423c5b48bb','N')
;

-- Mar 18, 2017 1:15:18 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107861,0.0,'ISO Currency Code','Three letter ISO 4217 Code of the Currency','For details - http://www.unece.org/trade/rec/rec09en.htm',1100271,'ISO_Code',3,'N','N','N','N','N','N',10,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:18','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:18','YYYY-MM-DD HH24:MI:SS'),100,328,'Y','N','U','N','de3ed45c-4c40-4f02-9b05-47bd8c64dc13','N')
;

-- Mar 18, 2017 1:15:18 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107862,0.0,'Description','Optional short description of the record','A description is limited to 255 characters.',1100271,'Description',255,'N','N','N','N','N','N',10,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:18','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:18','YYYY-MM-DD HH24:MI:SS'),100,275,'Y','Y','U','N','b08a8520-f85d-47f9-be70-da24b4056056','N')
;

-- Mar 18, 2017 1:15:18 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107863,0.0,'Process Instance','Instance of the process',1100271,'AD_PInstance_ID',10,'N','N','N','N','N','N',19,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:18','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:18','YYYY-MM-DD HH24:MI:SS'),100,114,'N','N','U','N','0985bd34-237f-4074-959c-8f9c19a51184','N','C')
;

-- Mar 18, 2017 1:15:19 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107864,0.0,'Sequence',1100271,'Sequence',10,'N','N','N','N','N','N',22,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:15:18','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:15:18','YYYY-MM-DD HH24:MI:SS'),100,52016,'Y','N','U','N','20fe9ddf-b8a9-43e0-9463-4f0f320c8e98','N','N')
;

-- Mar 18, 2017 1:15:37 PM ICT
UPDATE AD_Column SET FKConstraintName='Account_ttcsgeneralledgerview', FKConstraintType='N',Updated=TO_TIMESTAMP('2017-03-18 13:15:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1107849
;

-- Mar 18, 2017 1:15:37 PM ICT
INSERT INTO t_alter_column values('t_tcsgeneralledgerview','Account_ID','NUMERIC(10)',null,'NULL')
;

-- Mar 18, 2017 1:15:37 PM ICT
ALTER TABLE t_tcsgeneralledgerview ADD CONSTRAINT Account_ttcsgeneralledgerview FOREIGN KEY (Account_ID) REFERENCES c_elementvalue(c_elementvalue_id) DEFERRABLE INITIALLY DEFERRED
;

-- Mar 18, 2017 1:17:07 PM ICT
DELETE FROM AD_Table_Trl WHERE AD_Table_ID=1100271
;

-- Mar 18, 2017 1:17:07 PM ICT
DELETE FROM AD_Table WHERE AD_Table_ID=1100271
;

-- Mar 18, 2017 1:17:33 PM ICT
INSERT INTO AD_Table (AD_Table_ID,Name,TableName,LoadSeq,AccessLevel,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsSecurityEnabled,IsDeleteable,IsHighVolume,IsView,EntityType,ImportTable,IsChangeLog,ReplicationType,CopyColumnsFromTable,IsCentrallyMaintained,AD_Table_UU,Processing,DatabaseViewDrop,CopyComponentsFromView) VALUES (1100272,'t_tcsgeneralledgerview','t_tcsgeneralledgerview',0,'3',0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:33','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','N','N','U','N','N','L','N','Y','551926e0-99b7-43c2-8f15-870454121284','N','N','N')
;

-- Mar 18, 2017 1:17:40 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,AD_Val_Rule_ID,ColumnName,DefaultValue,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107865,0.0,'Client','Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.',1100272,129,'AD_Client_ID','@#AD_Client_ID@',10,'N','N','Y','N','N','N',19,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:39','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:39','YYYY-MM-DD HH24:MI:SS'),100,102,'N','N','U','N','9181ffca-184a-4e46-b452-844cdc101e33','N','D')
;

-- Mar 18, 2017 1:17:40 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,AD_Val_Rule_ID,ColumnName,DefaultValue,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107866,0.0,'Organization','Organizational entity within client','An organization is a unit of your client or legal entity - examples are store, department. You can share data between organizations.',1100272,104,'AD_Org_ID','@#AD_Org_ID@',10,'N','N','Y','N','N','N',19,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:40','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:40','YYYY-MM-DD HH24:MI:SS'),100,113,'N','N','U','N','d1b0d455-e790-4f11-af5a-ea0a3ad821bf','N','D')
;

-- Mar 18, 2017 1:17:40 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107867,0.0,'org_name',1100272,'org_name',60,'N','N','Y','N','N','N',10,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:40','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:40','YYYY-MM-DD HH24:MI:SS'),100,300017,'N','N','U','N','07ef7b44-c369-41f3-a472-c1c70bc60385','N','N')
;

-- Mar 18, 2017 1:17:40 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107868,0.0,'Accounting Schema','Rules for accounting','An Accounting Schema defines the rules used in accounting such as costing method, currency and calendar',1100272,'C_AcctSchema_ID',10,'N','N','N','N','N','N',19,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:40','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:40','YYYY-MM-DD HH24:MI:SS'),100,181,'N','N','U','N','1c087a5a-e0be-4b8c-bf53-5bfd1ea856bc','N','C')
;

-- Mar 18, 2017 1:17:41 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107869,0.0,'acct_schema_name',1100272,'acct_schema_name',60,'N','N','N','N','N','N',10,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:41','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:41','YYYY-MM-DD HH24:MI:SS'),100,1101256,'Y','N','U','N','1245f642-c99f-4a13-bd1a-595a16bb6b0a','N','N')
;

-- Mar 18, 2017 1:17:41 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Reference_Value_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107870,0.0,'Account','Account used','The (natural) account used',1100272,'Account_ID',10,'N','N','N','N','N','N',18,132,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:41','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:41','YYYY-MM-DD HH24:MI:SS'),100,148,'N','N','U','N','0f1e09ae-ae96-4e9e-b1ec-e30639fa820b','N')
;

-- Mar 18, 2017 1:17:41 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107871,0.0,'account_no',1100272,'account_no',40,'N','N','N','N','N','N',10,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:41','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:41','YYYY-MM-DD HH24:MI:SS'),100,1101257,'Y','N','U','N','de1ef0c3-db35-48ce-9258-d2a896452321','N','N')
;

-- Mar 18, 2017 1:17:41 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107872,0.0,'Account Name',1100272,'Account_Name',120,'N','N','N','N','N','N',14,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:41','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:41','YYYY-MM-DD HH24:MI:SS'),100,202278,'N','Y','U','N','bc6b7dbd-3769-4efb-b84d-bc63bf0a9c1c','N','N')
;

-- Mar 18, 2017 1:17:41 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107873,0.0,'Account Date','Accounting Date','The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.',1100272,'DateAcct',29,'N','N','N','N','N','N',15,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:41','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:41','YYYY-MM-DD HH24:MI:SS'),100,263,'N','N','U','N','7db25d5a-b2c1-47cd-9ad3-7ea2db07071c','N','N')
;

-- Mar 18, 2017 1:17:42 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107874,0.0,'Period','Period of the Calendar','The Period indicates an exclusive range of dates for a calendar.',1100272,'C_Period_ID',10,'N','N','N','N','N','N',19,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:41','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:41','YYYY-MM-DD HH24:MI:SS'),100,206,'N','N','U','N','468914ef-f27a-4c54-a0b9-cff62c13e88a','N','N')
;

-- Mar 18, 2017 1:17:42 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107875,0.0,'period_name',1100272,'period_name',60,'N','N','N','N','N','N',10,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:42','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:42','YYYY-MM-DD HH24:MI:SS'),100,1101258,'Y','N','U','N','58ecadcb-9f25-46d1-9ada-2a034fa11566','N','N')
;

-- Mar 18, 2017 1:17:42 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Reference_Value_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107876,0.0,'PostingType','The type of posted amount for the transaction','The Posting Type indicates the type of amount (Actual, Budget, Reservation, Commitment, Statistical) the transaction.',1100272,'PostingType',1,'N','N','N','N','N','N',17,125,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:42','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:42','YYYY-MM-DD HH24:MI:SS'),100,514,'Y','N','U','N','1b9b5a41-d7e5-4b8e-8e21-e00a6c99e1d1','N')
;

-- Mar 18, 2017 1:17:42 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107877,0.0,'Accounted Debit','Accounted Debit Amount','The Account Debit Amount indicates the transaction amount converted to this organization''s accounting currency',1100272,'AmtAcctDr',131089,'N','N','N','N','N','N',12,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:42','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:42','YYYY-MM-DD HH24:MI:SS'),100,162,'N','N','U','N','e65a7435-c903-416b-9acb-288ebb135959','N')
;

-- Mar 18, 2017 1:17:43 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107878,0.0,'Accounted Credit','Accounted Credit Amount','The Account Credit Amount indicates the transaction amount converted to this organization''s accounting currency',1100272,'AmtAcctCr',131089,'N','N','N','N','N','N',12,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:42','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:42','YYYY-MM-DD HH24:MI:SS'),100,161,'N','N','U','N','a73a2ce2-fd3a-45b9-bb8e-7c5150455996','N')
;

-- Mar 18, 2017 1:17:43 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107879,0.0,'Source Credit','Source Credit Amount','The Source Credit Amount indicates the credit amount for this line in the source currency.',1100272,'AmtSourceCr',131089,'N','N','N','N','N','N',12,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:43','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:43','YYYY-MM-DD HH24:MI:SS'),100,164,'N','N','U','N','6ae9a875-3f7f-481f-a3e3-0df529d23a97','N')
;

-- Mar 18, 2017 1:17:43 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107880,0.0,'Source Debit','Source Debit Amount','The Source Debit Amount indicates the credit amount for this line in the source currency.',1100272,'AmtSourceDr',131089,'N','N','N','N','N','N',12,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:43','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:43','YYYY-MM-DD HH24:MI:SS'),100,165,'N','N','U','N','e1b537b5-e2c0-487b-b13f-85f495ca8842','N')
;

-- Mar 18, 2017 1:17:44 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107881,0.0,'Accounted Balance','Accounted Balance Amount','The Account Balance Amount indicates the transaction amount converted to this organization''s accounting currency',1100272,'AmtAcctBalance',131089,'N','N','N','N','N','N',12,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:43','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:43','YYYY-MM-DD HH24:MI:SS'),100,2649,'N','N','U','N','a341e8e4-6c07-4725-a501-0a82edcb3847','N')
;

-- Mar 18, 2017 1:17:44 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107882,0.0,'ISO Currency Code','Three letter ISO 4217 Code of the Currency','For details - http://www.unece.org/trade/rec/rec09en.htm',1100272,'ISO_Code',3,'N','N','N','N','N','N',10,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:44','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:44','YYYY-MM-DD HH24:MI:SS'),100,328,'Y','N','U','N','4096f749-5275-4399-b3eb-bc8ec0c32354','N')
;

-- Mar 18, 2017 1:17:44 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton) VALUES (1107883,0.0,'Description','Optional short description of the record','A description is limited to 255 characters.',1100272,'Description',255,'N','N','N','N','N','N',10,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:44','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:44','YYYY-MM-DD HH24:MI:SS'),100,275,'Y','Y','U','N','e4bf906d-b3e2-4035-81ac-49b524c76c93','N')
;

-- Mar 18, 2017 1:17:44 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107884,0.0,'Process Instance','Instance of the process',1100272,'AD_PInstance_ID',10,'N','N','N','N','N','N',19,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:44','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:44','YYYY-MM-DD HH24:MI:SS'),100,114,'N','N','U','N','6978146b-9744-4174-8d78-1fab1db5ae94','N','C')
;

-- Mar 18, 2017 1:17:44 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,AD_Table_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsAlwaysUpdateable,AD_Column_UU,IsToolbarButton,FKConstraintType) VALUES (1107885,0.0,'Sequence',1100272,'Sequence',10,'N','N','N','N','N','N',22,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:44','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:44','YYYY-MM-DD HH24:MI:SS'),100,52016,'Y','N','U','N','2fedfd6e-20ac-47b1-be4c-78cf66f6a7d3','N','N')
;

-- Mar 18, 2017 1:17:56 PM ICT
INSERT INTO AD_ReportView (AD_ReportView_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,AD_Table_ID,EntityType,AD_ReportView_UU) VALUES (1100069,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,'General Ledger View',1100272,'U','d3c906dd-f49f-4f10-969b-306d0c93449b')
;

-- Mar 18, 2017 1:19:04 PM ICT
UPDATE AD_Process SET IsReport='Y', AD_ReportView_ID=1100069,Updated=TO_TIMESTAMP('2017-03-18 13:19:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=1100245
;

-- Mar 18, 2017 1:19:36 PM ICT
INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,Description,Help,AD_Process_ID,SeqNo,AD_Reference_ID,AD_Reference_Value_ID,IsRange,FieldLength,IsMandatory,ColumnName,IsCentrallyMaintained,EntityType,AD_Element_ID,AD_Process_Para_UU,IsEncrypted) VALUES (1100513,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:19:35','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:19:35','YYYY-MM-DD HH24:MI:SS'),100,'Account','Account used','The (natural) account used',1100245,10,30,331,'N',0,'N','Account_ID','Y','U',148,'23051bed-147b-4bc7-b704-5a534b54316c','N')
;

-- Mar 18, 2017 1:19:55 PM ICT
INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,Description,Help,AD_Process_ID,SeqNo,AD_Reference_ID,IsRange,FieldLength,IsMandatory,ColumnName,IsCentrallyMaintained,EntityType,AD_Element_ID,AD_Process_Para_UU,IsEncrypted) VALUES (1100514,0,0,'Y',TO_TIMESTAMP('2017-03-18 13:19:55','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:19:55','YYYY-MM-DD HH24:MI:SS'),100,'Account Date','Accounting Date','The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.',1100245,20,15,'Y',0,'Y','DateAcct','Y','U',263,'9f33d51f-6a6a-4851-941e-6fecd4abddd3','N')
;

-- Mar 18, 2017 1:20:01 PM ICT
UPDATE AD_Process_Para SET IsRange='Y', IsMandatory='Y',Updated=TO_TIMESTAMP('2017-03-18 13:20:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=1100513
;

-- Mar 18, 2017 1:21:05 PM ICT
INSERT INTO AD_Menu (AD_Menu_ID,Name,Description,"action",AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsSummary,AD_Process_ID,IsSOTrx,IsReadOnly,EntityType,IsCentrallyMaintained,AD_Menu_UU) VALUES (1100197,'Create General Ledger View','Create General Ledger View','P',0,0,'Y',TO_TIMESTAMP('2017-03-18 13:21:04','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-18 13:21:04','YYYY-MM-DD HH24:MI:SS'),100,'N',1100245,'N','N','U','Y','b1df0849-5d55-4422-9f81-3f724c361125')
;

-- Mar 18, 2017 1:21:05 PM ICT
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo, AD_TreeNodeMM_UU) SELECT t.AD_Client_ID, 0, 'Y', statement_timestamp(), 100, statement_timestamp(), 100,t.AD_Tree_ID, 1100197, 0, 999, Generate_UUID() FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.TreeType='MM' AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=1100197)
;

