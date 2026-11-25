package template;

import io.javelit.core.Jt;
import service.Service;

public class ReajustarProdutoUI {
    public static void render() {
        Jt.markdown("## Reajuste de preco dos produtos").use();
        Jt.markdown("Informe um valor positivo para aumento ou negativo para desconto.").use();
        var form = Jt.form().use();
        Number percentual = Jt.numberInput("Percentual (%)").value(0.0).use(form);

        if (Jt.formSubmitButton("Aplicar reajuste").use(form)) {
            try {
                double valor = percentual == null ? 0.0 : percentual.doubleValue();
                Service.produto_reajustar(valor);
                Jt.success("Reajuste aplicado com sucesso").use();
            } catch (Exception exception) {
                Jt.error(exception.getMessage()).use();
            }
        }
    }
}
