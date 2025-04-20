import { useState } from 'react';
import { fetchTicketCode, markTicketUsed } from './LippuFetchToiminnot';

const tyhjaScrummeritLippu = {
    id: '',
    koodi: '',
    lipputyyppi: '',
    hinta: '',
    tila: {
      id: '',
      tila: ''
    },
    tarkastanut: '',
    tarkastuspvm: '',
    myynti: {
      myyntipaiva: '',
      maksutapa: ''
    }
  }

export function tarkastaLippu() {
    const [koodi, setKoodi] = useState('');
    const [scrummeritLippu, setScrummeritLippu] = useState(tyhjaScrummeritLippu);
    const [errorMsg, setErrorMsg] = useState('');
    const [successMsg, setSuccessMsg] = useState('');
    const [loading, setLoading] = useState(false);
  
    const getTicket = async () => {
        if (!koodi) {
          setErrorMsg("Koodikenttä on tyhjä");
          return;
        }
      
        setLoading(true);
        const result = await fetchTicketCode(koodi);
      
        if (result.success && result.data) {
          setScrummeritLippu(result.data);
          setSuccessMsg('Lipun haku onnistui');
          setErrorMsg('');
        } else {
          setErrorMsg(result.message ?? "Tuntematon virhe");
        }
      
        setLoading(false);
      };
  
    const markAsUsed = async () => {
      if (!scrummeritLippu.id) {
        setErrorMsg("Lippua ei ole valittu");
        return;
      }
  
      setLoading(true);
      const result = await markTicketUsed(scrummeritLippu.id);
  
      if (result.success && result.data) {
        setScrummeritLippu(result.data);
        setSuccessMsg('Lippu merkitty tarkastetuksi');
        setErrorMsg('');
      } else {
        setErrorMsg(result.message ?? "Tuntematon virhe");
      }
      setLoading(false);
    };
  
    return {
      koodi,
      setKoodi,
      scrummeritLippu,
      getTicket,
      markAsUsed,
      errorMsg,
      successMsg,
      loading
    };
  }