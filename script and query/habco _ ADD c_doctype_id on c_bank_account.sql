-- Update Asset Register Script_PartII
-- Jul 14, 2017 3:50:54 PM ICT
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,AD_Val_Rule_ID,ColumnName,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,SeqNo,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,AD_Column_UU,IsAllowCopy,SeqNoSelection,IsToolbarButton,IsSecure,FKConstraintType) VALUES (305336,0,'Document Type','Document type or rules','The Document Type determines document sequence and processing rules',297,300118,'C_DocType_ID',10,'N','N','N','N','N',0,'N',19,0,0,'Y',TO_TIMESTAMP('2017-07-14 15:50:53','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-07-14 15:50:53','YYYY-MM-DD HH24:MI:SS'),100,196,'N','N','U','N','N','N','Y','dff5140e-e35f-4c40-8f60-c5085b8da9f1','Y',0,'N','N','N')
;

-- Jul 14, 2017 3:50:57 PM ICT
UPDATE AD_Column SET FKConstraintName='CDocType_CBankAccount', FKConstraintType='N',Updated=TO_TIMESTAMP('2017-07-14 15:50:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=305336
;

-- Jul 14, 2017 3:50:57 PM ICT
ALTER TABLE C_BankAccount ADD COLUMN C_DocType_ID NUMERIC(10) DEFAULT NULL 
;

-- Jul 14, 2017 3:50:58 PM ICT
ALTER TABLE C_BankAccount ADD CONSTRAINT CDocType_CBankAccount FOREIGN KEY (C_DocType_ID) REFERENCES c_doctype(c_doctype_id) DEFERRABLE INITIALLY DEFERRED
;

