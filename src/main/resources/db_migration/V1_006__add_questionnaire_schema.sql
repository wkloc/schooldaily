CREATE TABLE "question" (
	"id" BIGSERIAL NOT NULL,
	"created_by" BIGINT NOT NULL,
	"created_on" TIMESTAMP NOT NULL,
	"deleted_on" TIMESTAMP NULL DEFAULT NULL,
	"question_text" TEXT NOT NULL,
	"questionnaire_id" BIGINT NOT NULL
);

CREATE TABLE "questionnaire" (
	"id" BIGSERIAL NOT NULL,
	"created_by" BIGINT NOT NULL,
	"created_on" TIMESTAMP NOT NULL,
	"deleted_on" TIMESTAMP NULL DEFAULT NULL,
	"questionnaire_name" VARCHAR(255) NOT NULL,
	"questionnaire_description" TEXT NULL DEFAULT NULL
);

CREATE TABLE "questionnaire_instance" (
	"id" BIGSERIAL NOT NULL,
	"created_by" BIGINT NOT NULL,
	"created_on" TIMESTAMP NOT NULL,
	"deleted_on" TIMESTAMP NULL DEFAULT NULL,
	"questionnaire_id" BIGINT NOT NULL
);

CREATE TABLE "question_instance" (
	"id" BIGSERIAL NOT NULL,
	"created_by" BIGINT NOT NULL,
	"created_on" TIMESTAMP NOT NULL,
	"deleted_on" TIMESTAMP NULL DEFAULT NULL,
	"question_id" BIGINT NOT NULL,
	"answer" TEXT NULL DEFAULT NULL,
	"grade" INTEGER NULL DEFAULT NULL,
	"questionnaire_instance_id" BIGINT NOT NULL
);