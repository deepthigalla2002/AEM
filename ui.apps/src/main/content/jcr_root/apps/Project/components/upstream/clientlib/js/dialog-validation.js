(function ($, Granite) {
    $(document).on("dialog-ready", function () {
        $(".cq-dialog-submit").click(function (event) {
            let textValue = $("input[name='./textfield1']").val();
            let pathValue = $("input[name='./pathfield']").val();
            let textFieldValue = $("input[name='./textfield2']").val();
            let richText = $("textarea[name='./richtext']").val();  // Fixed selector

            if (!textValue || !pathValue || !textFieldValue || !richText) {
                event.preventDefault(); // Stop form submission
  			 new Coral.Dialog().set({
                    header: {
                        innerHTML: "Validation Error"
                    },
                    content: {
                        innerHTML: "None of the fields are authored in the cq:dialog."
                    },
                    footer: {
                        innerHTML: '<button is="coral-button" variant="primary" coral-close>OK</button>'
                    }
                }).show();
            }
        });

    });
})(jQuery, Granite);
