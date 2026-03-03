ALTER TABLE kontoauszug DROP COLUMN IF EXISTS dtype;
ALTER TABLE kontoauszug ALTER COLUMN haendler DROP NOT NULL;
ALTER TABLE kontoauszug ALTER COLUMN kartennummer DROP NOT NULL;
ALTER TABLE kontoauszug ALTER COLUMN empfaenger_iban DROP NOT NULL;
ALTER TABLE kontoauszug ALTER COLUMN empfaenger_name DROP NOT NULL;