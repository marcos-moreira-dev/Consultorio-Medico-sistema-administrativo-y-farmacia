/**
 * Genera un hash BCrypt para uso administrativo o seeds.
 *
 * Uso:
 * node scripts/generar-hash-bcrypt.js "Admin123*"
 */

const bcrypt = require('bcrypt');

async function main() {
  const rawPassword = process.argv[2];

  if (!rawPassword || typeof rawPassword !== 'string' || rawPassword.trim().length === 0) {
    console.error('Uso: node scripts/generar-hash-bcrypt.js "TuPasswordSegura"');
    process.exit(1);
  }

  const saltRounds = 10;
  const hash = await bcrypt.hash(rawPassword, saltRounds);

  console.log(hash);
}

main().catch((error) => {
  console.error('Error generando hash BCrypt:', error);
  process.exit(1);
});
