package br.com.rotafood.api.modules.common.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WhatsAppWebhookRequest(
        @NotBlank String object,

        @NotEmpty
        @Valid List<Entry> entry
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Entry(
            @NotBlank String id,

            @NotEmpty
            @Valid List<Change> changes
    ) {

        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Change(
                @NotBlank
                @Pattern(
                  regexp = "messages|message_status|conversation|business_phone_update|message_template_status_update"
                )
                String field,

                @NotNull @Valid Value value
        ) {

            @JsonIgnoreProperties(ignoreUnknown = true)
            public record Value(

                    @NotBlank
                    @JsonProperty("messaging_product")
                    String messagingProduct,

                    @Valid Metadata metadata,

                    @Valid List<Contact> contacts,

                    @Valid List<Message> messages,

                    @Valid List<Status> statuses,

                    @Valid List<Error> errors
            ) {


                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Metadata(
                        @NotBlank
                        @JsonProperty("display_phone_number")
                        String displayPhoneNumber,

                        @NotBlank
                        @JsonProperty("phone_number_id")
                        String phoneNumberId
                ) {}

                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Contact(
                        @NotBlank @JsonProperty("wa_id") String waId,
                        @Valid Profile profile
                ) {
                    public record Profile(@NotBlank String name) {}
                }

                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Message(
                        @NotBlank String id,
                        @NotBlank String from,
                        @NotBlank String timestamp,

                        @NotBlank String type,

                        @Valid Text text,
                        @Valid Image image,
                        @Valid Reaction reaction,
                        @Valid Location location
                ) {
                    public record Text(@NotBlank String body) {}
                    public record Image(
                            @NotBlank String id,
                            @NotBlank @JsonProperty("mime_type") String mimeType,
                            String caption,
                            @NotBlank String sha256
                    ) {}
                    public record Reaction(
                            @NotBlank @JsonProperty("message_id") String messageId,
                            @NotBlank String emoji
                    ) {}
                    public record Location(
                            @DecimalMin("-90.0") @DecimalMax("90.0") double latitude,
                            @DecimalMin("-180.0") @DecimalMax("180.0") double longitude,
                            String name,
                            String address
                    ) {}
                }

                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Status(
                        @NotBlank String id,
                        @NotBlank @JsonProperty("recipient_id") String recipientId,
                        @NotBlank String status,
                        @NotBlank String timestamp
                ) {}

                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Error(
                        @Positive int code,
                        @NotBlank String title,
                        @NotBlank String details
                ) {}
            }
        }
    }
}
