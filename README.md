# Informe de Evaluación – Automatización de Pruebas

## ✅ 1. Análisis del estado actual de la plataforma

### 1.1 Descripción del error en la lógica del código
En la clase `Usuario` se observa el siguiente método:
```java
public void actualizarPeso(double nuevoPeso) {
    // Implementación defectuosa
    this.peso -= 1;
}
```
En lugar de asignar el valor ingresado por el usuario (`nuevoPeso`), el sistema descuenta siempre 1 kg del peso actual. Esto introduce una lógica incorrecta y contradice el propósito de la aplicación.

### 1.2 Impacto en la experiencia del usuario
- **Inexactitud de datos**: El peso registrado no coincide con el valor real ingresado, afectando métricas de salud.
- **Desconfianza**: Usuarios perderán confianza en la plataforma al ver valores erróneos.
- **Errores acumulados**: Con múltiples actualizaciones, la discrepancia puede aumentar de forma descontrolada.
- **Riesgo clínico**: En aplicaciones de seguimiento de salud, datos erróneos pueden afectar decisiones médicas.

### 1.3 Falta de procesos de validación y pruebas
- **Ausencia de pruebas unitarias**: No hay verificación automática de la lógica de negocio (p.ej., `actualizarPeso`).
- **Sin pruebas funcionales/Integración**: No se valida el correcto flujo en la interfaz (login, navegación, actualización).
- **Carencia de regresión**: No hay mecanismos para detectar que errores anteriores reaparezcan tras cambios.
- **Sin pruebas de rendimiento**: No existe evaluación de cómo responde el sistema bajo carga.
- **Pipeline CI/CD inexistente**: Los cambios no se validan de forma continua, aumentando la probabilidad de errores en producción.

---

## ✅ 2. Diseño y desarrollo de pruebas automatizadas

### 2.1 Pruebas unitarias
**Herramienta**: JUnit 5  
**Justificación**:
- Integración nativa con Maven.
- Desarrollo ágil: feedback inmediato.
- Cobertura de lógica de negocio aislada.
**Implementación**:
- `UsuarioTest.java` validando:
  - Asignación correcta para valores positivos.
  - Comportamiento extremo (por ejemplo, 0.0 kg y valores negativos).

### 2.2 Pruebas funcionales
**Herramienta**: Selenium + ChromeDriver + WebDriverManager  
**Justificación**:
- Simulación real de interacciones de usuario en navegador.
- Cobertura end-to-end de flujos críticos: login, formulario de peso.
- **WebDriverManager** selecciona automáticamente la versión adecuada del driver según el navegador instalado, evitando incompatibilidades en distintos entornos (local, CI).
**Implementación**:
- `UsuarioFlowTest.java` en modo headless:
  - Carga la URL de login.
  - Realiza credenciales y navega a sección de peso.
  - Ingresa un nuevo valor y verifica la salida en el DOM.

### 2.3 Pruebas de regresión
**Estrategia**:
- Consolidar la suite de JUnit + Selenium.
- Ejecutar en cada PR y build para detectar regresiones.
- Mantener documentación y actualización de casos de prueba.

### 2.4 Pruebas de rendimiento
**Herramienta**: Apache JMeter  
**Justificación**:
- Amplio soporte para protocolos HTTP.
- Fácil parametrización de hilos concurrentes y ramp-up.
- Generación de reportes de latencia y throughput.
**Implementación**:
- Plan `performance/HealthTrackPlan.jmx`:
  - 50 usuarios concurrentes.
  - 10 iteraciones por hilo.
  - POST a `/api/usuario/peso`.
- Análisis de resultados de latencia media y percentiles.

---

## ✅ 3. Automatización del proceso de pruebas con CI/CD

### 3.1 Pipeline en GitHub Actions
- **Ventajas**:
  - Integración nativa con repositorio GitHub.
  - Entorno estándar (ubuntu-latest) con Chrome instalado.
  - Ejecuta pruebas sin servidor propio.
- **Configuración** (`.github/workflows/ci.yml`):
  1. **build-and-test**: `mvn clean test` ejecuta unitarias y funcionales.
  2. **performance**: instala JMeter y ejecuta plan de carga.
  3. **quality**: integra SonarQube Cloud para cobertura y análisis estático.

### 3.2 Reportes y alertas
- **Surefire**: informes en `target/surefire-reports`.
- **JMeter**: resultados en `performance/resultados.jtl`.
- **SonarCloud**: feedback de calidad en cada PR.

### 3.3 Validación de calidad de código
**Herramienta**: SonarQube  
- Detección de bugs, vulnerabilidades y código duplicado.
- Métricas de cobertura unitaria.
- Integración en pipeline para rechazo de builds con calidad insuficiente.

---

**Conclusión**: Con esta propuesta se corrige el error crítico de negocio, se añade cobertura de pruebas en todos los niveles (unitarias, funcionales, regresión y rendimiento) y se asegura la calidad mediante un pipeline CI/CD robusto.