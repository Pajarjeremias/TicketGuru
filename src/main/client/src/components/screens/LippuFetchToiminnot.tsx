import { config as scrummeriConfig } from '../../config/scrummerit';

export async function fetchTicketCode(koodi: string) {
    try {
        const result = await fetch(`${scrummeriConfig.apiBaseUrl}/liput?koodi=${koodi}`, {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`,
            }
        })
      
          if (result.status === 200) {
            const data = await result.json();
            return { success: true, data: { ...muunnettuData(data) } };
          }
      
          return { success: false, message: `Virhe: ${result.status}` };
      
        } catch (e) {
          return { success: false, message: e instanceof TypeError ? "CORS-virhe" : "Tuntematon virhe" };
        }
      }

      export async function markTicketUsed(id: string) {
        try {
          const result = await fetch(`${scrummeriConfig.apiBaseUrl}/liput/${id}`, {
            method: 'PATCH',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`,
            },
            body: JSON.stringify({
              used: new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('.')[0]
            })
          });
      
          if (result.status === 200) {
            const data = await result.json();
            return { success: true, data: { ...muunnettuData(data) } };
          }
          if (result.status === 400) {
            return { success: false, message: "Lippu on jo tarkistettu!" };
          }
          return { success: false, message: `Virhe: ${result.status}` };
        } catch (e) {
          return { success: false, message: e instanceof TypeError ? "CORS-virhe" : "Tuntematon virhe" };
        }
      }
      
      function muunnettuData(data: any) {
        return {
          id: data.lippu_id,
          koodi: data.koodi,
          lipputyyppi: data.tapahtuman_lipputyyppi.lipputyyppi.lipputyyppi,
          hinta: data.hinta,
          tila: {
            id: data.tila.tila_id,
            tila: data.tila.tila
          },
          tarkastanut: data.tarkastanut ?? '',
          tarkastuspvm: data.tarkastus_pvm ?? '',
          myynti: {
            myyntipaiva: data.myynti.myyntipaiva,
            maksutapa: data.myynti.maksutapa.maksutapa
          }
        }
      }