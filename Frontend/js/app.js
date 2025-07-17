const API_BASE = "http://localhost:8080/api/v1/event"; // Adjust if your backend runs elsewhere

// Elements
const eventForm = document.getElementById('eventForm');
const formTitle = document.getElementById('formTitle');
const resetFormBtn = document.getElementById('resetFormBtn');
const submitBtn = document.getElementById('submitBtn');
const updateBtn = document.getElementById('updateBtn');

const eventIdEl = document.getElementById('eventId');
const eventNameEl = document.getElementById('eventName');
const eventDateEl = document.getElementById('eventDate');
const eventBookedEl = document.getElementById('eventBooked');
const eventLocationEl = document.getElementById('eventLocation');
const eventDescriptionEl = document.getElementById('eventDescription');
const eventStatusEl = document.getElementById('eventStatus');

const eventTableBody = document.querySelector('#eventTable tbody');
const searchInput = document.getElementById('searchInput');
const searchBtn = document.getElementById('searchBtn');
const reloadBtn = document.getElementById('reloadBtn');

// Status modal elements
const statusModalEl = document.getElementById('statusModal');
const statusModalEventName = document.getElementById('statusModalEventName');
const confirmStatusChangeBtn = document.getElementById('confirmStatusChangeBtn');
const statusModal = new bootstrap.Modal(statusModalEl);
let statusChangeTargetId = null;

/* ------------------ Helpers ------------------ */
function showToast(message, variant = 'primary', delay = 3000) {
    const id = `toast-${Date.now()}`;
    const wrapper = document.createElement('div');
    wrapper.innerHTML = `
      <div id="${id}" class="toast align-items-center text-bg-${variant} border-0" role="alert" aria-live="assertive" aria-atomic="true" data-bs-delay="${delay}">
        <div class="d-flex">
          <div class="toast-body">${message}</div>
          <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
      </div>`;
    const alertContainer = document.getElementById('alertContainer');
    alertContainer.appendChild(wrapper.firstElementChild);
    const toastEl = document.getElementById(id);
    const toast = new bootstrap.Toast(toastEl);
    toast.show();
    toastEl.addEventListener('hidden.bs.toast', () => toastEl.remove());
}

function clearForm() {
    eventIdEl.value = '';
    eventNameEl.value = '';
    eventDateEl.value = '';
    eventBookedEl.value = '';
    eventLocationEl.value = '';
    eventDescriptionEl.value = '';
    eventStatusEl.value = 'PLANNED';
    formTitle.textContent = 'Create Event';
    submitBtn.classList.remove('d-none');
    updateBtn.classList.add('d-none');
}

function fillForm(event) {
    eventIdEl.value = event.id ?? '';
    eventNameEl.value = event.eventName ?? '';
    eventDateEl.value = event.eventDate ?? '';
    eventBookedEl.value = event.eventBooked ? toLocalDateTimeInput(event.eventBooked) : '';
    eventLocationEl.value = event.eventLocation ?? '';
    eventDescriptionEl.value = event.eventDescription ?? '';
    eventStatusEl.value = event.eventStatus ?? 'PLANNED';
    formTitle.textContent = 'Update Event';
    submitBtn.classList.add('d-none');
    updateBtn.classList.remove('d-none');
}

// Convert ISO-like string to value acceptable by <input type="datetime-local">
function toLocalDateTimeInput(isoString) {
    // Accept both '2025-07-17T12:30:00' and '2025-07-17T12:30:00Z'
    const d = new Date(isoString);
    if (isNaN(d.getTime())) return '';
    const pad = n => String(n).padStart(2,'0');
    return `${d.getFullYear()}-${pad(d.getMonth()+1)}-${pad(d.getDate())}T${pad(d.getHours())}:${pad(d.getMinutes())}`;
}

// Convert datetime-local control back to ISO (no zone) expected by backend's LocalDateTime
function fromLocalDateTimeInput(val) {
    if (!val) return null; // backend may accept null
    // value is like '2025-07-17T12:30'
    return val + ':00'; // append seconds; adjust if needed
}

// Build DTO JSON from form
function buildEventDTO() {
    const idVal = eventIdEl.value ? Number(eventIdEl.value) : null;
    return {
        id: idVal,
        eventName: eventNameEl.value.trim() || null,
        eventDate: eventDateEl.value || null, // yyyy-mm-dd; backend LocalDate
        eventBooked: fromLocalDateTimeInput(eventBookedEl.value), // LocalDateTime
        eventLocation: eventLocationEl.value.trim() || null,
        eventDescription: eventDescriptionEl.value.trim() || null,
        eventStatus: eventStatusEl.value || 'PLANNED'
    };
}

/* ------------------ API Calls ------------------ */
async function apiCreate(dto) {
    const res = await fetch(`${API_BASE}/create`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dto)
    });
    if (!res.ok) throw new Error(`Create failed: ${res.status}`);
}

async function apiUpdate(dto) {
    const res = await fetch(`${API_BASE}/update`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dto)
    });
    if (!res.ok) throw new Error(`Update failed: ${res.status}`);
}

async function apiGetAll() {
    const res = await fetch(`${API_BASE}/all`);
    if (!res.ok) throw new Error(`Load failed: ${res.status}`);
    return res.json();
}

async function apiSearch(keyword) {
    const res = await fetch(`${API_BASE}/search/${encodeURIComponent(keyword)}`);
    if (!res.ok) throw new Error(`Search failed: ${res.status}`);
    return res.json();
}

async function apiToggleStatus(id) {
    const res = await fetch(`${API_BASE}/status/${id}`, { method: 'PATCH' });
    if (!res.ok) throw new Error(`Status change failed: ${res.status}`);
}

/* ------------------ Table Rendering ------------------ */
function renderEvents(events) {
    eventTableBody.innerHTML = '';
    if (!Array.isArray(events) || !events.length) {
        const tr = document.createElement('tr');
        const td = document.createElement('td');
        td.colSpan = 7;
        td.className = 'text-center py-4 text-muted';
        td.textContent = 'No events found.';
        tr.appendChild(td);
        eventTableBody.appendChild(tr);
        return;
    }

    for (const ev of events) {
        const tr = document.createElement('tr');
        tr.innerHTML = `
        <td>${ev.id ?? ''}</td>
        <td>${ev.eventName ?? ''}</td>
        <td>${ev.eventDate ?? ''}</td>
        <td>${ev.eventBooked ? toLocalDateTimeInput(ev.eventBooked) : ''}</td>
        <td>${ev.eventLocation ?? ''}</td>
        <td>${renderStatusBadge(ev.eventStatus)}</td>
        <td class="text-end">
          <div class="btn-group btn-group-sm" role="group">
            <button type="button" class="btn btn-outline-primary btn-edit" data-id="${ev.id}">Edit</button>
            <button type="button" class="btn btn-outline-secondary btn-status" data-id="${ev.id}" data-name="${encodeURIComponent(ev.eventName ?? '')}">Status</button>
          </div>
        </td>`;
        eventTableBody.appendChild(tr);
    }
}

function renderStatusBadge(status) {
    const safe = (status || 'PLANNED').toUpperCase();
    return `<span class="badge badge-status ${safe}">${safe}</span>`;
}

/* ------------------ Load / Search ------------------ */
async function loadEvents() {
    try {
        const data = await apiGetAll();
        renderEvents(data);
    } catch (err) {
        console.error(err);
        showToast('Failed to load events.', 'danger');
    }
}

async function doSearch() {
    const keyword = searchInput.value.trim();
    if (!keyword) {
        loadEvents();
        return;
    }
    try {
        const data = await apiSearch(keyword);
        renderEvents(data);
        showToast(`Found ${data.length} matching event(s).`, 'info');
    } catch (err) {
        console.error(err);
        showToast('Search failed.', 'danger');
    }
}

/* ------------------ Event Listeners ------------------ */
eventForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const dto = buildEventDTO();
    try {
        await apiCreate(dto);
        showToast('Event created!', 'success');
        clearForm();
        loadEvents();
    } catch (err) {
        console.error(err);
        showToast(err.message, 'danger');
    }
});

updateBtn.addEventListener('click', async () => {
    const dto = buildEventDTO();
    if (!dto.id) {
        showToast('Missing ID for update.', 'warning');
        return;
    }
    try {
        await apiUpdate(dto);
        showToast('Event updated!', 'success');
        clearForm();
        loadEvents();
    } catch (err) {
        console.error(err);
        showToast(err.message, 'danger');
    }
});

resetFormBtn.addEventListener('click', clearForm);

searchBtn.addEventListener('click', doSearch);
searchInput.addEventListener('keyup', (e) => {
    if (e.key === 'Enter') doSearch();
});

reloadBtn.addEventListener('click', loadEvents);

// Delegate table action buttons
eventTableBody.addEventListener('click', async (e) => {
    const btn = e.target.closest('button');
    if (!btn) return;
    const id = btn.dataset.id;
    if (btn.classList.contains('btn-edit')) {
        // We need the event data. Since we don't have getById endpoint, read row cells.
        const row = btn.closest('tr');
        const ev = {
            id: Number(id),
            eventName: row.children[1].textContent,
            eventDate: row.children[2].textContent || null,
            eventBooked: row.children[3].textContent ? row.children[3].textContent : null,
            eventLocation: row.children[4].textContent,
            eventStatus: row.children[5].textContent.trim(),
            eventDescription: '' // unknown (not in table); user can re-enter
        };
        fillForm(ev);
        window.scrollTo({ top: 0, behavior: 'smooth' });
    } else if (btn.classList.contains('btn-status')) {
        statusChangeTargetId = id;
        statusModalEventName.textContent = decodeURIComponent(btn.dataset.name);
        statusModal.show();
    }
});

confirmStatusChangeBtn.addEventListener('click', async () => {
    if (!statusChangeTargetId) return;
    try {
        await apiToggleStatus(statusChangeTargetId);
        showToast('Status changed!', 'success');
        statusModal.hide();
        loadEvents();
    } catch (err) {
        console.error(err);
        showToast('Failed to change status.', 'danger');
    } finally {
        statusChangeTargetId = null;
    }
});

/* ------------------ Init ------------------ */
document.addEventListener('DOMContentLoaded', loadEvents);

