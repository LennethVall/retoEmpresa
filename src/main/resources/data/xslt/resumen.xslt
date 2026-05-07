<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html lang="es">
            <head>
                <meta charset="UTF-8"/>
                <title>Resumen de Eventos - Botón Loco</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
                <style>
                    body { background-color: #f8f9fa; }
                    .user-card { margin-bottom: 2rem; border-radius: 15px; overflow: hidden; }
                </style>
            </head>
            <body>
                <div class="container py-5">
                    <div class="text-center mb-5">
                        <h1 class="display-4 fw-bold text-primary">🕹️ Dashboard de Eventos</h1>
                        <p class="lead text-muted">Análisis de interacción del usuario con el "Botón Loco"</p>
                    </div>

                    <xsl:for-each select="eventos/usuario">
                        <div class="card shadow-sm user-card">
                            <div class="card-header bg-dark text-white">
                                <h3 class="mb-0">👤 Usuario: <xsl:value-of select="@nombre"/></h3>
                            </div>
                            <div class="card-body">
                                <table class="table table-hover align-middle">
                                    <thead class="table-light">
                                        <tr>
                                            <th>Acción / Estímulo</th>
                                            <th class="text-center">Frecuencia</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <xsl:for-each select="evento">
                                            <tr>
                                                <td class="fw-semibold text-capitalize">
                                                    <xsl:value-of select="@tipo"/>
                                                </td>
                                                <td class="text-center">
                                                    <span class="badge rounded-pill bg-info text-dark px-3 py-2">
                                                        <xsl:value-of select="@repeticiones"/> clics
                                                    </span>
                                                </td>
                                            </tr>
                                        </xsl:for-each>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </xsl:for-each>

                    <footer class="text-center mt-5 text-muted">
                        <small>Proyecto 1º DAM - Generado automáticamente desde XML/XSLT</small>
                    </footer>
                </div>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>