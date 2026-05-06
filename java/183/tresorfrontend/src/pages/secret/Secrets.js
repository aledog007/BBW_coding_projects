import '../../App.css';
import React, {useEffect, useState} from 'react';
import {getSecretsforUser, deleteSecret, updateSecret} from "../../comunication/FetchSecrets";

/**
 * Secrets - Hauptkomponente zur Anzeige und Verwaltung der Tresor-Inhalte.
 * Unterstützt das Anzeigen, Bearbeiten und Löschen von verschlüsselten Secrets.
 * 
 * @author Peter Rutschmann / Antigravity
 */
const Secrets = ({loginValues}) => {
    const [secrets, setSecrets] = useState([]);
    const [errorMessage, setErrorMessage] = useState('');
    const [editingSecret, setEditingSecret] = useState(null);
    const [editContent, setEditContent] = useState({});

    // Funktion zum Laden der Secrets vom Server
    const fetchSecrets = async () => {
        setErrorMessage('');
        if (!loginValues || !loginValues.email) {
            console.error('Secrets: Kein Login vorhanden.');
            setErrorMessage("Bitte loggen Sie sich zuerst ein.");
            return;
        }
        try {
            const data = await getSecretsforUser(loginValues);
            // Content verarbeiten: Falls es ein String ist (vom Server entschlüsselt), in JSON parsen
            const processedData = data.map(s => {
                let parsedContent = s.content;
                if (typeof s.content === 'string') {
                    try {
                        // Da der Server bei Entschlüsselungsfehlern einen Fehler-String zurückgeben kann,
                        // prüfen wir ob es wirklich JSON ist.
                        if (s.content.trim().startsWith("{") || s.content.trim().startsWith("[")) {
                            parsedContent = JSON.parse(s.content);
                        }
                    } catch (e) {
                        console.error("Fehler beim Parsen des Contents für ID " + s.id, e);
                    }
                }
                return { ...s, content: parsedContent };
            });
            setSecrets(processedData);
        } catch (error) {
            console.error('Fetch fehlgeschlagen:', error.message);
            setErrorMessage(error.message);
        }
    };

    useEffect(() => {
        fetchSecrets();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [loginValues]);

    // Löschen eines Secrets
    const handleDelete = async (secretId) => {
        if (window.confirm("Sind Sie sicher, dass Sie dieses Secret löschen möchten?")) {
            try {
                await deleteSecret({loginValues, secretId});
                fetchSecrets(); // Liste aktualisieren
            } catch (error) {
                alert("Fehler beim Löschen: " + error.message);
            }
        }
    };

    // Bearbeitungs-Modus starten
    const handleEditClick = (secret) => {
        setEditingSecret(secret);
        setEditContent({...secret.content}); // Kopie des Inhalts zum Bearbeiten
    };

    // Update zum Server senden
    const handleUpdate = async (e) => {
        e.preventDefault();
        try {
            await updateSecret({loginValues, secretId: editingSecret.id, content: editContent});
            setEditingSecret(null); // Modal schließen
            fetchSecrets(); // Liste aktualisieren
        } catch (error) {
            alert("Fehler beim Aktualisieren: " + error.message);
        }
    };

    /**
     * Hilfsfunktion: Stellt den Content je nach Typ (kindid) hübsch dar.
     */
    const renderSecretDetails = (content) => {
        if (!content) return <i>Kein Inhalt</i>;
        
        // Falls ein Entschlüsselungsfehler vorliegt, wird dieser als String angezeigt
        if (typeof content === 'string') {
            return <span style={{color: '#e11d48', fontWeight: '500'}}>{content}</span>;
        }
        
        if (content.error) return <span style={{color: 'red'}}>{content.error}</span>;
        
        switch(content.kindid) {
            case 1: // Zugangsdaten
                return (
                    <div className="secret-details">
                        <p><strong>User:</strong> {content.userName}</p>
                        <p><strong>URL:</strong> <a href={content.url} target="_blank" rel="noreferrer" className="secret-link">{content.url}</a></p>
                    </div>
                );
            case 2: // Kreditkarte
                return (
                    <div className="secret-details">
                        <p><strong>Typ:</strong> {content.cardtype}</p>
                        <p><strong>Nummer:</strong> {content.cardnumber}</p>
                        <p><strong>Ablauf:</strong> {content.expiration}</p>
                    </div>
                );
            case 3: // Notiz
                return (
                    <div className="secret-details">
                        <p><strong>Titel:</strong> {content.title}</p>
                        <p className="secret-note-preview">
                            {content.content?.length > 50 ? content.content.substring(0, 50) + "..." : content.content}
                        </p>
                    </div>
                );
            default:
                return <pre className="raw-json">{JSON.stringify(content, null, 2)}</pre>;
        }
    };

    return (
        <div className="secrets-container">
            <div className="secrets-header">
                <h1>Meine Secrets</h1>
                <button className="btn btn-refresh" onClick={fetchSecrets}>
                    Aktualisieren
                </button>
            </div>
            
            {errorMessage && <div className="error-banner">{errorMessage}</div>}

            <div className="table-wrapper">
                <table className="secrets-table">
                    <thead>
                        <tr>
                            <th>Typ</th>
                            <th>Inhalt</th>
                            <th style={{textAlign: 'right'}}>Aktionen</th>
                        </tr>
                    </thead>
                    <tbody>
                        {secrets?.length > 0 ? (
                            secrets.map(secret => (
                                <tr key={secret.id} className="secret-row">
                                    <td style={{width: '140px'}}>
                                        <span className={`badge badge-${secret.content?.kind || 'unknown'}`}>
                                            {secret.content?.kind || 'Unbekannt'}
                                        </span>
                                    </td>
                                    <td>{renderSecretDetails(secret.content)}</td>
                                    <td className="actions-cell">
                                        <button className="btn btn-edit" onClick={() => handleEditClick(secret)}>Edit</button>
                                        <button className="btn btn-delete" onClick={() => handleDelete(secret.id)}>Delete</button>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan="3" className="empty-state">
                                    Keine Secrets vorhanden. Erstellen Sie ein neues Secret unter "Save New Secret".
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>

            {/* Modal für die Bearbeitung */}
            {editingSecret && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h2>Secret bearbeiten ({editingSecret.content?.kind || 'Unbekannt'})</h2>
                            <button className="close-btn" onClick={() => setEditingSecret(null)}>&times;</button>
                        </div>
                        <form onSubmit={handleUpdate} className="modal-form">
                            {/* Dynamische Felder basierend auf dem Typ des Secrets */}
                            {editingSecret.content?.kindid === 1 && (
                                <>
                                    <div className="form-group">
                                        <label>Benutzername</label>
                                        <input value={editContent.userName || ''} onChange={e => setEditContent({...editContent, userName: e.target.value})} required />
                                    </div>
                                    <div className="form-group">
                                        <label>Passwort</label>
                                        <input type="text" value={editContent.password || ''} onChange={e => setEditContent({...editContent, password: e.target.value})} required />
                                    </div>
                                    <div className="form-group">
                                        <label>URL</label>
                                        <input value={editContent.url || ''} onChange={e => setEditContent({...editContent, url: e.target.value})} />
                                    </div>
                                </>
                            )}
                            {editingSecret.content?.kindid === 2 && (
                                <>
                                    <div className="form-group">
                                        <label>Karten-Typ</label>
                                        <input value={editContent.cardtype || ''} onChange={e => setEditContent({...editContent, cardtype: e.target.value})} required />
                                    </div>
                                    <div className="form-group">
                                        <label>Karten-Nummer</label>
                                        <input value={editContent.cardnumber || ''} onChange={e => setEditContent({...editContent, cardnumber: e.target.value})} required />
                                    </div>
                                    <div className="form-group">
                                        <label>Ablaufdatum</label>
                                        <input value={editContent.expiration || ''} onChange={e => setEditContent({...editContent, expiration: e.target.value})} placeholder="MM/YY" />
                                    </div>
                                </>
                            )}
                            {editingSecret.content?.kindid === 3 && (
                                <>
                                    <div className="form-group">
                                        <label>Titel</label>
                                        <input value={editContent.title || ''} onChange={e => setEditContent({...editContent, title: e.target.value})} required />
                                    </div>
                                    <div className="form-group">
                                        <label>Inhalt</label>
                                        <textarea rows="4" value={editContent.content || ''} onChange={e => setEditContent({...editContent, content: e.target.value})} required />
                                    </div>
                                </>
                            )}
                            
                            <div className="modal-footer">
                                <button type="button" className="btn btn-cancel" onClick={() => setEditingSecret(null)}>Abbrechen</button>
                                <button type="submit" className="btn btn-save">Änderungen speichern</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Secrets;