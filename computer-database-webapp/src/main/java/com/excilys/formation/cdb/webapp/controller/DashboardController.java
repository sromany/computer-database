package com.excilys.formation.cdb.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.cdb.binding.CompanyMapperDTO;
import com.excilys.formation.cdb.binding.ComputerMapper;
import com.excilys.formation.cdb.binding.ComputerMapperDTO;
import com.excilys.formation.cdb.core.ComputerDTO;
import com.excilys.formation.cdb.core.entity.CompanyEntity;
import com.excilys.formation.cdb.core.entity.ComputerEntity;
import com.excilys.formation.cdb.service.ServiceCompany;
import com.excilys.formation.cdb.service.ServiceComputer;
import com.excilys.formation.cdb.service.ServiceManagerException;
import com.excilys.formation.cdb.service.pages.PagesComputer;

@Controller
public class DashboardController {

	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DashboardController.class);
	private static final String DASHBOARD_EXCEPTION = "DashBoardControllerException: {}";
	private final int CENTER_VALUE = 2;

	private PagesComputer<ComputerDTO> pages;
	private ServiceComputer serviceComputer;
	private ServiceCompany serviceCompany;
	private ComputerMapperDTO computerMDTO;
	private CompanyMapperDTO companyMDTO;
	private ComputerMapper computerMap;

	@Autowired
	public DashboardController(PagesComputer<ComputerDTO> pages, 
			ComputerMapperDTO computerMDTO,
			ComputerMapper computerMap,
			CompanyMapperDTO companyMDTO,
			ServiceComputer serviceComputer,
			ServiceCompany serviceCompany) {
		this.pages = pages;
		this.serviceComputer = serviceComputer;
		this.serviceCompany = serviceCompany;
		this.computerMDTO = computerMDTO;
		this.computerMap = computerMap;
		this.companyMDTO = companyMDTO;
	}

	@GetMapping("/dashboard")
	public ModelAndView dashboard(@RequestParam(value = "page", defaultValue = "0") int pageUrlParam,
			@RequestParam(value = "stride", defaultValue = "10") int strideUrlParam) throws ServiceManagerException {
		ModelAndView modelAndView = new ModelAndView();

		logger.info("PageUrl:{}", String.valueOf(pageUrlParam));
		logger.info("StrideUrl:{}", String.valueOf(strideUrlParam));

		Page<ComputerEntity> pageComputer = Page.empty();

		try {
			pages.setStride(strideUrlParam);
			pages.goTo(pageUrlParam);
			pageComputer = serviceComputer.getList(pages.getCurrentPage(), pages.getStride());

			// Mapping Inutile : les entities sont relativement simples pour être postées
			// sur les Jsps.

			int numberOfPages = pages.getNumberOfPages();
			int currentPage = pages.getCurrentPage();
			int focus = currentPage < CENTER_VALUE ? CENTER_VALUE
					: ( currentPage >= CENTER_VALUE && currentPage <= (numberOfPages - 3) ? currentPage
							: numberOfPages - 3 );

			logger.info("{}; {}; {}; {}; {}", pages.getOffset(), pages.getStride(), numberOfPages, currentPage, focus);

			modelAndView.addObject("numberOfElements", pages.getNumberOfElements());
			modelAndView.addObject("numberOfPages", pages.getNumberOfPages());
			modelAndView.addObject("pageComputer", pageComputer.getContent());
			modelAndView.addObject("currentPage", pages.getCurrentPage());
			modelAndView.addObject("stride", pages.getStride());
			modelAndView.addObject("focus", focus);
		} catch (Exception e) {
			logger.error(DASHBOARD_EXCEPTION, e.getMessage());
			modelAndView = new ModelAndView();
			modelAndView.setViewName("redirect:500");
		}
		return modelAndView;
	}

	@PostMapping("/dashboard")
	public @ResponseBody ResponseEntity<String> postDelete() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/addComputer")
	public ModelAndView add() {
		ModelAndView modelAndView = new ModelAndView();
		try {
			Page<CompanyEntity> pageCompany = serviceCompany.getAllList();
			modelAndView.addObject("companies", pageCompany.getContent());
		} catch (Exception e) {
			logger.debug("AddComputerServletException: {}", e.getMessage(), e);
		}
		return modelAndView;
	}

	@PostMapping("/addComputer")
	public ModelAndView postAdd(@ModelAttribute("ComputerDTO") ComputerDTO cmp) {
		ModelAndView modelAndView = new ModelAndView();
		logger.info("{}", cmp);
		try {
			serviceComputer.createComputer(cmp);
		} catch (ServiceManagerException e) {
			logger.error("{}", e.getMessage(), e);
			modelAndView = new ModelAndView();
			modelAndView.setViewName("redirect:500");
		}
		return modelAndView;
	}

	@GetMapping("/editComputer")
	public ModelAndView edit(@RequestParam(value = "id", defaultValue = "1") long idComputer) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			ComputerDTO computerDTO = computerMDTO.map(serviceComputer.getComputer(idComputer));
			Page<CompanyEntity> pageCompany = serviceCompany.getAllList();
			modelAndView.addObject("computer", computerDTO);
			modelAndView.addObject("companies", pageCompany.getContent());
		} catch (Exception e) {
			logger.debug("AddComputerServletException: {}", e.getMessage(), e);
		}
		return modelAndView;
	}

	@PostMapping("/editComputer")
	public ModelAndView postEdit(@ModelAttribute("ComputerDTO") ComputerDTO cmp) {
		ModelAndView modelAndView = new ModelAndView("editComputer");
		logger.info("{}", cmp);
		try {
			ComputerEntity computer = serviceComputer.getComputer(cmp.getId());
			serviceComputer.updateComputer(computer);
		} catch (ServiceManagerException e) {
			logger.error("{}", e.getMessage(), e);
			modelAndView = new ModelAndView();
			modelAndView.setViewName("redirect:500");
		}
		return modelAndView;
	}

	@GetMapping("/403")
	public ModelAndView error403() {
		return new ModelAndView();
	}

	@GetMapping("/404")
	public ModelAndView error404() {
		return new ModelAndView();
	}

	@GetMapping("/500")
	public ModelAndView error500() {
		return new ModelAndView();
	}

}