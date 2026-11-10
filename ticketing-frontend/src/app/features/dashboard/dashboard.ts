import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import {
  DashboardService,
  DashboardStatistics
} from '../../core/services/dashboard';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss'
})
export class Dashboard implements OnInit {

  userName = 'User';
  loading = true;
  errorMessage = '';

  statistics = [
    { title: 'Total Tickets', count: 0, color: '#2563eb' },
    { title: 'Open', count: 0, color: '#f59e0b' },
    { title: 'In Progress', count: 0, color: '#8b5cf6' },
    { title: 'Resolved', count: 0, color: '#16a34a' }
  ];

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.loadStatistics();
  }

  loadStatistics(): void {
    this.dashboardService.getStatistics().subscribe({
      next: (data: DashboardStatistics) => {
        this.statistics[0].count = data.totalTickets;
        this.statistics[1].count = data.openTickets;
        this.statistics[2].count = data.inProgressTickets;
        this.statistics[3].count = data.resolvedTickets;

        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Unable to load dashboard statistics.';
        this.loading = false;
      }
    });
  }
}
